package com.cts.cartservice.repository;

import com.cts.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.userId = :userId")
    int deleteAllByUserId(@Param("userId") Long userId);

    Optional<CartItem> findByUserIdAndItemId(Long userId, Long itemId);
    void deleteByUserIdAndItemId(Long userId, Long itemId);


}
