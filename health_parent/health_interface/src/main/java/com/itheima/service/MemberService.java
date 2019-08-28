package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
public interface MemberService {

    /**
     * 根据手机号码查询Member
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 增加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据月份查询会员数量
     * @param monthsList
     * @return
     */
    List<Integer> getMemberReport(List<String> monthsList);
}
