package com.yuanben.service;

import com.yuanben.common.InvalidException;
import com.yuanben.model.Metadata;
import com.yuanben.model.http.*;
import com.yuanben.util.GsonUtil;
import com.yuanben.util.HttpUtil;
import com.yuanben.util.SecretUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>原本链node节点处理器</p>
 * <p>支持向node节点发送和查询metadata，以及查询license</p>
 */
public class NodeProcessor {

    /**
     * 向node节点查询metadata
     *
     * @param url     node节点的地址 （http://localhost:9000)
     * @param version node节点的版本 （默认v1)
     * @param dna     metadata对应的闪电dna
     * @return metadata的查询结果体
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static MetadataQueryResp QueryMetadata(String url, String version, String dna) throws InvalidException {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(dna)) {
            throw new InvalidException("url or DNA is empty");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/metadata/" + dna;
        String s = HttpUtil.sendGet(url);
        return GsonUtil.getInstance().fromJson(s, MetadataQueryResp.class);
    }

    /**
     * 向node节点注册metadata
     *
     * @param url     node节点的地址 （http://localhost:9000)
     * @param version node节点的版本 （默认v1)
     * @param async   字段已废弃
     * @param md      要注册的metadata，不需要传content
     * @return metadata的注册结果体
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static MetadataSaveResp SaveMetadata(String url, String version, Metadata md) throws InvalidException {
        if (StringUtils.isBlank(url)) {
            throw new InvalidException("url is empty");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/metadata";
        if (md == null) {
            throw new InvalidException("metadata is null");
        }
        if (StringUtils.isBlank(md.getSignature())) {
            throw new InvalidException("signature is null");
        }
        if (md.getLicense() == null || StringUtils.isBlank(md.getLicense().getType()) || MapUtils.isEmpty(md.getLicense().getParameters())) {
            throw new InvalidException("license is null");
        }
        String s = HttpUtil.sendPost(url, md.toJson());
        return GsonUtil.getInstance().fromJson(s, MetadataSaveResp.class);
    }

    /**
     * 向node节点查询license
     *
     * @param url         node节点的地址 （http://localhost:9000)
     * @param version     node节点的版本 （默认v1)
     * @param licenseType license's type
     * @return license的查询结果体
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static LicenseQueryResp QueryLicense(String url, String version, String licenseType) throws InvalidException {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(licenseType)) {
            throw new InvalidException("url or licenseType is empty");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/license/" + licenseType;
        String s = HttpUtil.sendGet(url);
        return GsonUtil.getInstance().fromJson(s, LicenseQueryResp.class);

    }

    /**
     * 向node节点查询最新的blockHash
     *
     * @param url     node节点的地址 （http://localhost:9000)
     * @param version node节点的版本 （默认v1)
     * @return 最新的blcokHash封装
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static BlockHashQueryResp QueryLatestBlockHash(String url, String version) throws InvalidException {
        if (StringUtils.isBlank(url)) {
            throw new InvalidException("url is empty");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/block_hash/";
        String s = HttpUtil.sendGet(url);
        return GsonUtil.getInstance().fromJson(s, BlockHashQueryResp.class);
    }

    /**
     * 向node节点查询blockHash是否在链上，并处于指定高度
     *
     * @param url     node节点的地址 （http://localhost:9000)
     * @param version node节点的版本 （默认v1)
     * @param req     请求体 （包括blockHash和blockHeight)
     * @return 查询结果封装
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static BlockHashCheckResp CheckBlockHash(String url, String version, BlockHashCheckReq req) throws InvalidException {
        if (StringUtils.isBlank(url) || req == null) {
            throw new InvalidException("url or request body is empty");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/check_block_hash/";
        String s = HttpUtil.sendPost(url, req.toJson());
        return GsonUtil.getInstance().fromJson(s, BlockHashCheckResp.class);
    }

    /**
     * 注册公钥
     *
     * @param url     node节点的地址 （http://localhost:9000)
     * @param version node节点的版本 （默认v1)
     * @param req     请求体
     * @return 返回结果封装
     * @throws InvalidException 参数有误或网络请求错误
     */
    public static RegisterAccountResp RegisterAccount(String url, String version, RegisterAccountReq req) throws InvalidException {
        if (StringUtils.isBlank(url) || req == null) {
            throw new InvalidException("url or request body is empty");
        }
        if (!SecretUtil.CheckPublicKey(req.getPubKey(), true) ||
                StringUtils.isEmpty(req.getSignature()) ||
                req.getSubPubKeys() == null || req.getSubPubKeys().length < 1) {
            throw new InvalidException("request parameters error");
        }
        if (StringUtils.isBlank(version)) {
            version = "v1";
        }
        url += "/" + version + "/accounts/";
        String s = HttpUtil.sendPost(url, req.toJson());
        return GsonUtil.getInstance().fromJson(s, RegisterAccountResp.class);
    }
}
