package io.nuls.dapp.communitygovernance.mapper;

import io.nuls.dapp.communitygovernance.model.TbApplicant;
import io.nuls.dapp.communitygovernance.model.TbApplicantParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TbApplicantMapper {
    long countByExample(TbApplicantParam example);

    int deleteByExample(TbApplicantParam example);

    int deleteByPrimaryKey(String address);

    int insert(TbApplicant record);

    int insertSelective(TbApplicant record);

    List<TbApplicant> selectByExampleWithBLOBs(TbApplicantParam example);

    List<TbApplicant> selectByExample(TbApplicantParam example);

    TbApplicant selectByPrimaryKey(String address);

    int updateByExampleSelective(@Param("record") TbApplicant record, @Param("example") TbApplicantParam example);

    int updateByExampleWithBLOBs(@Param("record") TbApplicant record, @Param("example") TbApplicantParam example);

    int updateByExample(@Param("record") TbApplicant record, @Param("example") TbApplicantParam example);

    int updateByPrimaryKeySelective(TbApplicant record);

    int updateByPrimaryKeyWithBLOBs(TbApplicant record);

    int updateByPrimaryKey(TbApplicant record);

    List<TbApplicant> selectByVoter(@Param("voter") String voter);

    int updateSubtractAmountByVoter(@Param("voter") String voter, @Param("amount") BigDecimal amount, @Param("now") long now);

    int updateAddAmountByVoter(@Param("voter") String voter, @Param("amount") BigDecimal amount, @Param("now") long now);
}