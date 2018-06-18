package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.dto.UserDTO;
import com.minimall.boilerplate.business.entity.User;
import com.minimall.boilerplate.common.CheckUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import javax.persistence.criteria.Predicate;
import java.util.List;

import static java.util.Objects.nonNull;

public class UserSpecification {
    public static Specification<User> conditionQuerySpec(UserDTO userDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(userDTO)) {
                String userName = userDTO.getUserName();
                String userId = userDTO.getUserId();
                String userPhone = userDTO.getUserPhone();
                String searchContant = userDTO.getSearchContant();
                if(!CheckUtils.isEmpty(searchContant)){
                    predicates.add(builder.or(builder.or(builder.like(root.get("userName"), "%" + searchContant + "%"),
                            builder.like(root.get("userId"), "%" + searchContant + "%"),
                            builder.like(root.get("userPhone"), "%" + searchContant + "%"))));
                }

                if(!CheckUtils.isEmpty(userName)){
                    predicates.add(builder.like(root.get("userName"), "%" + userName + "%"));
                }
                if(!CheckUtils.isEmpty(userId)){
                    predicates.add(builder.like(root.get("userId"), "%" + userId + "%"));
                }
                if(!CheckUtils.isEmpty(userPhone)){
                    predicates.add(builder.like(root.get("userPhone"), "%" + userPhone + "%"));
                }
            }
            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
