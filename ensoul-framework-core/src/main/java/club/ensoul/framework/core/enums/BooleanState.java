package club.ensoul.framework.core.enums;

/**
 * boolean状态枚举类
 */
public enum BooleanState {
    
    是(1, true),
    否(0, false),
    
    yes(1, true),
    no(0, false),
    
    YES(1, true),
    NO(0, false),
    
    TRUE(1, true),
    FALSE(0, false),
    
    on(1, true),
    off(0, false),
    
    ON(1, true),
    OFF(0, false);
    
    public int key;
    public boolean value;
    
    BooleanState(int key, boolean value) {
        this.key = key;
        this.value = value;
    }
    
}