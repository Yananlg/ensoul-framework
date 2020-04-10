package club.ensoul.framework.shiro.config;

import club.ensoul.framework.shiro.annotation.ShiroFilter;
import club.ensoul.framework.shiro.annotation.ShiroHashedCredentials;
import club.ensoul.framework.shiro.annotation.ShiroRealm;
import club.ensoul.framework.shiro.annotation.ShiroSessionListener;
import club.ensoul.framework.shiro.credential.RetryLimitHashedCredentialsMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import javax.servlet.Filter;
import java.util.*;

@Slf4j
public class ShiroBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {


    public static final Map<String, List<Realm>> realmBeanMap = new LinkedHashMap<>();
    public static final List<Filter> shiroFilters = new LinkedList<>();
    public static final List<SessionListener> shiroSessionListeners = new LinkedList<>();

    /**
     * 扫描指定包及其子包下面拥有指定注解的类。
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        //是否使用默认的filter，使用默认的filter意味着只扫描那些类上拥有Component、Service、Repository或Controller注解的类。
//        ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(false);
//
//        // AssignableTypeFilter 指定包及其子包下面能赋值给指定Class的Class
//        // AnnotationTypeFilter 指定包及其子包下面拥有指定注解的类
//        // TypeExcludeFilter
//        // AspectJTypeFilter Type filter that uses AspectJ type pattern for matching.
//        // RegexPatternTypeFilter
//
//        TypeFilter authRealmFilter = new AnnotationTypeFilter(ShiroRealm.class);
//        TypeFilter authCredentialsFilter = new AnnotationTypeFilter(ShiroHashedCredentials.class);
//        beanScanner.addIncludeFilter(authRealmFilter);
//        beanScanner.addIncludeFilter(authCredentialsFilter);
//
//        String basePackage = "club.ensoul.thoughts.centre";
//        Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackage);
//
//        for (BeanDefinition beanDefinition : beanDefinitions) {
//            //beanName通常由对应的BeanNameGenerator来生成，比如Spring自带的AnnotationBeanNameGenerator、DefaultBeanNameGenerator等，也可以自己实现。
//            String beanName = beanDefinition.getBeanClassName();
//            assert beanName != null;
//            registry.registerBeanDefinition(beanName, beanDefinition);
//            realmBeans.add(beanName);
//        }
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        Map<String, Object> realmAnnotations = beanFactory.getBeansWithAnnotation(ShiroRealm.class);
        for (Map.Entry<String, Object> entry : realmAnnotations.entrySet()) {

            Class<?> type = beanFactory.getType(entry.getKey());
            ShiroRealm shiroRealm = Objects.requireNonNull(type).getAnnotation(ShiroRealm.class);
            List<Realm> realms = realmBeanMap.computeIfAbsent(shiroRealm.value(), k -> new ArrayList<>());
            AuthorizingRealm value = (AuthorizingRealm) entry.getValue();

            // 设置加密验证实例
            if (Objects.requireNonNull(type).isAnnotationPresent(ShiroHashedCredentials.class)) {
                ShiroHashedCredentials shiroHashedCredentials = Objects.requireNonNull(type).getAnnotation(ShiroHashedCredentials.class);
                try {
                    CredentialsMatcher credentialsMatcher = credentialsMatcher(shiroRealm.credentials(), shiroHashedCredentials);
                    value.setCredentialsMatcher(credentialsMatcher);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new FatalBeanException("Shiro CredentialsMatcher newInstance fail: ", e);
                }
            }

            realms.add(value);
        }

        Map<String, Object> filterAnnotations = beanFactory.getBeansWithAnnotation(ShiroFilter.class);
        for (Map.Entry<String, Object> entry : filterAnnotations.entrySet()) {
            shiroFilters.add((Filter) entry.getValue());
        }

        Map<String, Object> listenerAnnotations = beanFactory.getBeansWithAnnotation(ShiroSessionListener.class);
        for (Map.Entry<String, Object> entry : listenerAnnotations.entrySet()) {
            shiroSessionListeners.add((SessionListener) entry.getValue());
        }

    }

    private CredentialsMatcher credentialsMatcher(Class<? extends CredentialsMatcher> classz, ShiroHashedCredentials annotation)
            throws IllegalAccessException, InstantiationException {

        CredentialsMatcher credentialsMatcher = classz.newInstance();
        if (classz == HashedCredentialsMatcher.class) {
            HashedCredentialsMatcher hashedCredentialsMatcher = (HashedCredentialsMatcher) credentialsMatcher;
            hashedCredentialsMatcher.setHashAlgorithmName(annotation.value().name());
            hashedCredentialsMatcher.setHashIterations(annotation.iterations());
            hashedCredentialsMatcher.setStoredCredentialsHexEncoded(annotation.hex());
        } else if (classz == RetryLimitHashedCredentialsMatcher.class) {
            RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = (RetryLimitHashedCredentialsMatcher) credentialsMatcher;
            retryLimitHashedCredentialsMatcher.setHashAlgorithmName(annotation.value().name());
            retryLimitHashedCredentialsMatcher.setHashIterations(annotation.iterations());
            retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(annotation.hex());
            retryLimitHashedCredentialsMatcher.setRetryLimit(annotation.retryLimit());
        }
        return credentialsMatcher;
    }

}
