package YHWLTH.sharing.repo.impl;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.QShareItemListDTO;
import YHWLTH.sharing.dto.response.ShareItemListDTO;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.repo.custom.ShareItemCustomRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static YHWLTH.sharing.entity.QShareItem.shareItem;

@RequiredArgsConstructor
public class ShareItemRepoImpl implements ShareItemCustomRepo {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShareItemListDTO> shareItemList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable();

        List<ShareItemListDTO> content = jpaQueryFactory
                .select(new QShareItemListDTO(
                        shareItem.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared
                ))
                .from(shareItem)
                .where(
                        userIdEq(pageRequestDTO.getUserId()),
                        itemTypeEq(pageRequestDTO.getItemType()),
                        isShareEq(pageRequestDTO.getIsShared()),
                        depositLoe(pageRequestDTO.getDeposit()),
                        rentalFeeLoe(pageRequestDTO.getRentalFee())
                )
                .orderBy(shareItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ShareItemListDTO> countQuery = jpaQueryFactory
                .select(new QShareItemListDTO(
                        shareItem.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared
                ))
                .from(shareItem)
                .where(
                        itemTypeEq(pageRequestDTO.getItemType()),
                        isShareEq(pageRequestDTO.getIsShared()),
                        depositLoe(pageRequestDTO.getDeposit()),
                        rentalFeeLoe(pageRequestDTO.getRentalFee())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : shareItem.user.id.eq(userId);
    }

    private BooleanExpression itemTypeEq(ItemType itemType) {
        return itemType == null ? null : shareItem.itemType.eq(itemType.getDesc());
    }

    private BooleanExpression isShareEq(Boolean isShared) {
        return isShared == null ? null : shareItem.isShared.eq(isShared);
    }

    private BooleanExpression depositLoe(Long deposit) {
        return deposit == null ? null : shareItem.deposit.loe(deposit);
    }

    private BooleanExpression rentalFeeLoe(Long rentalFee) {
        return rentalFee == null ? null : shareItem.rentalFee.loe(rentalFee);
    }
}