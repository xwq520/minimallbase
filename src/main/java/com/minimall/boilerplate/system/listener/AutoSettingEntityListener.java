package com.minimall.boilerplate.system.listener;

import com.minimall.boilerplate.business.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.lang.reflect.Method;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.ReflectionUtils.findMethod;
import static org.springframework.util.ReflectionUtils.invokeMethod;

/**
 * Title: Entity监听器.
 * <p>Description: 保存Entity之前自动设置创建者, 创建时间, 更新者, 更新时间.</p>
 *
 */
public class AutoSettingEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        Class sClass = entity.getClass();

        Long id = getUserId();

        Method method = findMethod(sClass, "setCreatorId", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, id);

        method = findMethod(sClass, "setUpdaterId", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, id);

        Long timestamp = System.currentTimeMillis();

        method = findMethod(sClass, "setCreateTime", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, timestamp);

        method = findMethod(sClass, "setUpdateTime", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, timestamp);

    }

    @PreUpdate
    public void preUpdate(Object entity) {
        Class sClass = entity.getClass();

        Long id = getUserId();

        Method method = findMethod(sClass, "setUpdaterId", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, id);

        Long timestamp = System.currentTimeMillis();

        method = findMethod(sClass, "setUpdateTime", Long.class);
        if(nonNull(method))
            invokeMethod(method, entity, timestamp);
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(isNull(authentication))
            return 0L;
        Object principal = authentication.getPrincipal();
        if(isNull(principal) || !(principal instanceof UserDTO))
            return 0L;
        return ((UserDTO)principal).getId();
    }
}
