/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pnoker.center.dbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pnoker.center.dbs.mapper.RtmpMapper;
import com.pnoker.center.dbs.service.RtmpService;
import com.pnoker.common.base.PageInfo;
import com.pnoker.common.model.rtmp.Rtmp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>Rtmp 接口实现
 *
 * @author : pnoker
 * @email : pnokers@icloud.com
 */
@Slf4j
@Service
public class RtmpServiceImpl implements RtmpService {
    @Autowired
    private RtmpMapper rtmpMapper;

    @Override
    public boolean add(Rtmp rtmp) {
        return rtmpMapper.insert(rtmp) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return rtmpMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(Rtmp rtmp) {
        return rtmpMapper.updateById(rtmp) > 0;
    }

    @Override
    public IPage<Rtmp> list(Rtmp rtmp, PageInfo pageInfo) {
        QueryWrapper<Rtmp> queryWrapper = new QueryWrapper<>();
        query(rtmp, queryWrapper);
        Page<Rtmp> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        order(pageInfo.getOrders(), page);
        return rtmpMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Rtmp selectById(Long id) {
        return rtmpMapper.selectById(id);
    }

    @Override
    public void query(Rtmp rtmp, QueryWrapper<Rtmp> queryWrapper) {
        if (null != rtmp.getAutoStart()) {
            queryWrapper.eq("auto_start", BooleanUtils.isTrue(rtmp.getAutoStart()));
        }
        if (StringUtils.isNotBlank(rtmp.getName())) {
            queryWrapper.like("name", rtmp.getName());
        }
    }

    @Override
    public void order(List<OrderItem> orders, Page<Rtmp> page) {
        Optional.ofNullable(orders).ifPresent(orderItems -> {
            List<OrderItem> tmps = new ArrayList<>();
            orderItems.stream().forEach(orderItem -> {
                if ("id".equals(orderItem.getColumn())) {
                    orderItem.setAsc(BooleanUtils.isTrue(orderItem.isAsc()));
                    tmps.add(orderItem);
                }
                if ("name".equals(orderItem.getColumn())) {
                    orderItem.setAsc(BooleanUtils.isTrue(orderItem.isAsc()));
                    tmps.add(orderItem);
                }
            });
            page.setOrders(tmps);
        });
    }

}
