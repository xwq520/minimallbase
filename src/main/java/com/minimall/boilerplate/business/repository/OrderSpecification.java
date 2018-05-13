package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.dto.OrderDTO;
import com.minimall.boilerplate.business.entity.Order;
import com.minimall.boilerplate.common.CheckUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;


public class OrderSpecification {
    public static Specification<Order> conditionQuerySpec(OrderDTO orderDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(orderDTO)) {
                Integer orderStatus = orderDTO.getOrderStatus();

                if(!CheckUtils.isEmpty(orderStatus)){
                    predicates.add(builder.equal(root.get("orderStatus"),orderStatus));
                }
            }
            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
