package club.ensoul.framework.shiro.config;

import club.ensoul.framework.shiro.credential.RetryLimitHashedCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationPropertiesBinding
public class ShiroGenericConverter implements GenericConverter {

    private static final Set<ConvertiblePair> CONVERTIBLE_PAIR_SET = new HashSet<>();

    static  {
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, Realm.class));
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, Filter.class));
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, SessionListener.class));
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, CredentialsMatcher.class));
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, AuthenticationStrategy.class));
        CONVERTIBLE_PAIR_SET.add(new ConvertiblePair(String.class, RetryLimitHashedCredentialsMatcher.class));
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_PAIR_SET;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getObjectType().isAssignableFrom(byte[].class)) {
            return source.toString().getBytes();
        }
        try {
            return Class.forName(ObjectUtils.nullSafeToString(source)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
