/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.nuls.dapp.communitygovernance.processor.proposal;

import com.alibaba.fastjson.JSONObject;
import io.nuls.core.model.StringUtils;
import io.nuls.dapp.communitygovernance.constant.Constant;
import io.nuls.dapp.communitygovernance.event.proposal.CreateProposalEvent;
import io.nuls.dapp.communitygovernance.mapper.TbAliasMapper;
import io.nuls.dapp.communitygovernance.mapper.TbProposalMapper;
import io.nuls.dapp.communitygovernance.model.TbAlias;
import io.nuls.dapp.communitygovernance.model.TbAliasParam;
import io.nuls.dapp.communitygovernance.model.TbProposal;
import io.nuls.dapp.communitygovernance.model.contract.EventJson;
import io.nuls.dapp.communitygovernance.service.IEventProcessor;
import io.nuls.dapp.communitygovernance.service.api.AccountServiceApi;
import io.nuls.dapp.communitygovernance.util.TimeUtil;
import io.nuls.v2.txdata.ContractData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: Charlie
 * @date: 2019/8/26
 */
@Service
public class CreateProposalEventProcessor implements IEventProcessor {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TbProposalMapper tbProposalMapper;
    @Resource
    private TbAliasMapper tbAliasMapper;
    @Resource
    private AccountServiceApi accountServiceApi;
    @Value("${app.contract.address}")
    private String contractAddress;

    private static final String CREATE_PROPOSAL_EVENT = "CreateProposalEvent";

    @Override
    public void execute(String hash, int txType, ContractData contractData, EventJson eventJson) throws Exception {
        String contractAddress = eventJson.getContractAddress();
        if (!contractAddress.equals(contractAddress)) {
            return;
        }
        String event = eventJson.getEvent();
        if (!CREATE_PROPOSAL_EVENT.equals(event)) {
            return;
        }
        JSONObject payload = eventJson.getPayload();
        CreateProposalEvent createProposalEvent = payload.toJavaObject(CreateProposalEvent.class);
        String address =  createProposalEvent.getOwner();
        TbProposal tbProposal = new TbProposal();
        tbProposal.setProposalId(createProposalEvent.getId());
        tbProposal.setName(createProposalEvent.getName());
        tbProposal.setDesc(createProposalEvent.getDesc());
        tbProposal.setType((byte) createProposalEvent.getType());
        tbProposal.setEmail(createProposalEvent.getEmail());
        tbProposal.setOwner(address);
        tbProposal.setStatus(Constant.INREVIEW);
        tbProposal.setCounts(0);
        tbProposal.setFavour(BigDecimal.ZERO);
        tbProposal.setAgainst(BigDecimal.ZERO);
        tbProposal.setAbstention(BigDecimal.ZERO);
        tbProposal.setRefund((byte) 0);
        long now = TimeUtil.now();
        tbProposal.setCreateTime(now);
        tbProposal.setUpdateTime(now);

        //记录地址别名
        TbAliasParam tbAliasParam = new TbAliasParam();
        tbAliasParam.createCriteria().andAddressEqualTo(address);
        if(tbAliasMapper.countByExample(tbAliasParam) == 0L) {
            //查别名
            String alias = accountServiceApi.getAddressAlias(address);
            if(StringUtils.isNotBlank(alias)) {
                tbAliasMapper.insert(new TbAlias(address, alias));
            }
        }
        logger.debug("CreateProposalEvent success height:{}", eventJson.getBlockNumber());
    }
}
