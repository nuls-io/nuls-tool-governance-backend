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

package io.nuls.dapp.communitygovernance.processor.vote;

import io.nuls.dapp.communitygovernance.mapper.TbVoteItemMapper;
import io.nuls.dapp.communitygovernance.mapper.TbVoteMapper;
import io.nuls.dapp.communitygovernance.model.contract.EventJson;
import io.nuls.dapp.communitygovernance.service.IEventProcessor;
import io.nuls.v2.txdata.ContractData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * @author: Charlie
 * @date: 2019/8/30
 */
public class VoteCreateEventProcessor implements IEventProcessor {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TbVoteMapper tbVoteMapper;
    @Resource
    private TbVoteItemMapper tbVoteItemMapper;

    @Value("${app.contract.address}")
    private String contractAddress;

    private static final String VOTE_CREATE_EVENT = "VoteCreateEvent";
    @Override
    public void execute(String hash, int txType, ContractData contractData, EventJson eventJson) throws Exception {

    }
}