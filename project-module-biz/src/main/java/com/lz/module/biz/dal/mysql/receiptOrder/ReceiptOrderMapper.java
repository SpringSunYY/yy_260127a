package com.lz.module.biz.dal.mysql.receiptOrder;

import com.lz.framework.common.pojo.PageResult;
import com.lz.framework.mybatis.core.mapper.BaseMapperX;
import com.lz.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.lz.module.biz.controller.admin.receiptOrder.vo.ReceiptOrderPageReqVO;
import com.lz.module.biz.dal.dataobject.receiptOrder.ReceiptOrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 收款信息 Mapper
 *
 * @author YY
 */
@Mapper
public interface ReceiptOrderMapper extends BaseMapperX<ReceiptOrderDO> {

    default PageResult<ReceiptOrderDO> selectPage(ReceiptOrderPageReqVO reqVO) {
        LambdaQueryWrapperX<ReceiptOrderDO> queryWrapper = initReceiptOrderQuery(reqVO);
        return selectPage(reqVO, queryWrapper);
    }

    default LambdaQueryWrapperX<ReceiptOrderDO> initReceiptOrderQuery(ReceiptOrderPageReqVO reqVO) {
        return new LambdaQueryWrapperX<ReceiptOrderDO>()
                .likeIfPresent(ReceiptOrderDO::getReceiptNo, reqVO.getReceiptNo())
                .eqIfPresent(ReceiptOrderDO::getReceiptType, reqVO.getReceiptType())
                .eqIfPresent(ReceiptOrderDO::getProjectType, reqVO.getProjectType())
                .eqIfPresent(ReceiptOrderDO::getProjectScatteredType, reqVO.getProjectScatteredType())
                .likeIfPresent(ReceiptOrderDO::getProjectNo, reqVO.getProjectNo())
                .eqIfPresent(ReceiptOrderDO::getFiscalYear, reqVO.getFiscalYear())
                .likeIfPresent(ReceiptOrderDO::getProjectName, reqVO.getProjectName())
                .likeIfPresent(ReceiptOrderDO::getPayerName, reqVO.getPayerName())
                .betweenIfPresent(ReceiptOrderDO::getReceiptDate, reqVO.getReceiptDate())
                .eqIfPresent(ReceiptOrderDO::getReceiptMethod, reqVO.getReceiptMethod())
                .eqIfPresent(ReceiptOrderDO::getIsInvoiced, reqVO.getIsInvoiced())
                .betweenIfPresent(ReceiptOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReceiptOrderDO::getId);
    }

    default BigDecimal getReceiptOrderAmount(ReceiptOrderPageReqVO pageReqVO){
        LambdaQueryWrapperX<ReceiptOrderDO> queryWrapper = initReceiptOrderQuery(pageReqVO);
        // 先查询符合条件的所有记录，然后在内存中计算总和
        List<ReceiptOrderDO> list = selectList(queryWrapper.select(ReceiptOrderDO::getReceiptAmount));
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return list.stream()
                .map(ReceiptOrderDO::getReceiptAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}