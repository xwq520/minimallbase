package com.minimall.boilerplate.business.repository;

import com.minimall.boilerplate.business.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query(value = "select   * from \n" +
            "( select    max(ord.commodityNo) as commodityNo ,  max(ord.commodityName )as commodityName , sum(ord.purchaseQuantity)  as countNum  from mi_order   ord \n" +
            " where ord.userId =  ?1   \n" +
            " and ord.deletedAt = 0 \n" +
            " and (ord.orderStatus = 2 or ord.orderStatus = 3 )\n" +
            " group by ord.commodityNo \n" +
            " limit 0,20\n" +
            " ) as orderObj  \n" +
            "order by orderObj.countNum desc ",nativeQuery = true)
    List<Object[]>  findByOrderVolumeList(String userId);

    @Query(value = "SELECT  sum(ord.purchaseQuantity) as sumCount ,sum(ord.orderMoney) as sumMoney  , \n" +
            "\n" +
            "(select  count(subord.id) as waritCount  from mi_order   subord \n" +
            "where subord.userId =   ord.userId \n" +
            "and subord.orderStatus = 1  and subord.deletedAt = 0 )   as waritCount\n" +
            "\n" +
            " FROM mi_order   ord\n" +
            "where ord.userId =  ?1 and (ord.orderStatus = 2  or ord.orderStatus = 3 ) \n" +
            "and ord.deletedAt = 0\n " ,nativeQuery = true)
    List<Object[]> findByCountOrderInfo(String userId);
}
