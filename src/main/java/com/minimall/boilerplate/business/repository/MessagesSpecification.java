package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.dto.MessagesDTO;
import com.minimall.boilerplate.business.entity.Messages;
import com.minimall.boilerplate.common.CheckUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.nonNull;

public class MessagesSpecification {
    public static Specification<Messages> conditionQuerySpec(MessagesDTO messagesDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(messagesDTO)) {
                /*Long userId = messagesDTO.getUserId();

                if(!CheckUtils.isEmpty(userId)){
                    predicates.add(builder.equal(root.get("user").get("id"),userId));
                }*/
            }
            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
