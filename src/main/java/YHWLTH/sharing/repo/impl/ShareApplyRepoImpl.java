package YHWLTH.sharing.repo.impl;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.QShareApplyListDTO;
import YHWLTH.sharing.dto.response.ShareApplyListDTO;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.repo.custom.ShareApplyCustomRepo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static YHWLTH.sharing.entity.QShareApply.shareApply;
import static YHWLTH.sharing.entity.QShareItem.shareItem;

@RequiredArgsConstructor
public class ShareApplyRepoImpl implements ShareApplyCustomRepo {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShareApplyListDTO> shareApplyList(PageRequestDTO pageRequestDTO, Long userId) {
        Pageable pageable = pageRequestDTO.getPageable();

        List<ShareApplyListDTO> content = jpaQueryFactory
                .select(new QShareApplyListDTO(
                        shareItem.id,
                        shareApply.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared,
                        shareApply.startDate,
                        shareApply.endDate
                ))
                .from(shareApply)
                .leftJoin(shareApply.shareItem, shareItem)
                .where(
                        userIdEq(userId),
                        itemTypeEq(pageRequestDTO.getItemType()),
                        isShareEq(pageRequestDTO.getIsShared()),
                        depositLoe(pageRequestDTO.getDeposit()),
                        rentalFeeLoe(pageRequestDTO.getRentalFee())
                )
                .orderBy(shareApply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ShareApplyListDTO> countQuery = jpaQueryFactory
                .select(new QShareApplyListDTO(
                        shareItem.id,
                        shareApply.id,
                        shareItem.itemType,
                        shareItem.rentalFee,
                        shareItem.deposit,
                        shareItem.isShared,
                        shareApply.startDate,
                        shareApply.endDate
                ))
                .from(shareApply)
                .leftJoin(shareApply.shareItem, shareItem)
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
        return userId == null ? null : shareApply.user.id.eq(userId);
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