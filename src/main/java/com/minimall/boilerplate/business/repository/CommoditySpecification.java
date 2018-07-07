package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.dto.CommodityDTO;
import com.minimall.boilerplate.business.entity.Commodity;
import com.minimall.boilerplate.common.CheckUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class CommoditySpecification {
    public static Specification<Commodity> conditionQuerySpec(CommodityDTO commodityDTO) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nonNull(commodityDTO)) {
                String searchContant = commodityDTO.getSearchContant();
                predicates.add(builder.equal(root.get("user").get("userId"),commodityDTO.getUserId()));

                if(!CheckUtils.isEmpty(commodityDTO.getCommodityStatus())){
                    predicates.add(builder.equal(root.get("commodityStatus"),commodityDTO.getCommodityStatus()));
                }

                if(!CheckUtils.isEmpty(searchContant)){
                    predicates.add(builder.or(builder.or(builder.like(root.get("headline"), "%" + searchContant + "%"),
                            builder.like(root.get("subtitle"), "%" + searchContant + "%"),
                            builder.like(root.get("comNo"), "%" + searchContant + "%"))));
                }

            }
            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
