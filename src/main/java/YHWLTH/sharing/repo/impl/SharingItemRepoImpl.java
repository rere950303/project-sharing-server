package YHWLTH.sharing.repo.impl;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.QSharingItemListDTO;
import YHWLTH.sharing.dto.response.SharingItemListDTO;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.repo.custom.SharingItemCustomRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static YHWLTH.sharing.entity.QShareItem.shareItem;
import static YHWLTH.sharing.entity.QSharingItem.sharingItem;

@RequiredArgsConstructor
public class SharingItemRepoImpl implements SharingItemCustomRepo {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SharingItemListDTO> sharingItemList(PageRequestDTO pageRequestDTO, Long userId) {
        Pageable pageable = pageRequestDTO.getPageable();

        List<SharingItemListDTO> content = jpaQueryFactory
                .select(new QSharingItemListDTO(
                        shareItem.id,
                        sharingItem.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared,
                        sharingItem.startDate,
                        sharingItem.endDate
                ))
                .from(sharingItem)
                .leftJoin(sharingItem.shareItem, shareItem)
                .where(
                        userIdEq(userId),
                        itemTypeEq(pageRequestDTO.getItemType()),
                        isShareEq(pageRequestDTO.getIsShared()),
                        depositLoe(pageRequestDTO.getDeposit()),
                        rentalFeeLoe(pageRequestDTO.getRentalFee())
                )
                .orderBy(sharingItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<SharingItemListDTO> countQuery = jpaQueryFactory
                .select(new QSharingItemListDTO(
                        shareItem.id,
                        sharingItem.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared,
                        sharingItem.startDate,
                        sharingItem.endDate
                ))
                .from(sharingItem)
                .leftJoin(sharingItem.shareItem)
                .where(
                        userIdEq(userId),
                        itemTypeEq(pageRequestDTO.getItemType()),
                        isShareEq(pageRequestDTO.getIsShared()),
                        depositLoe(pageRequestDTO.getDeposit()),
                        rentalFeeLoe(pageRequestDTO.getRentalFee())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : sharingItem.user.id.eq(userId);
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
