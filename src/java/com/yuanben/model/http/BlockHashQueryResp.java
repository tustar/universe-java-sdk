package com.yuanben.model.http;

import com.google.gson.annotations.SerializedName;
import com.yuanben.util.GsonUtil;

/**
 * <p>查询最新的blockHash的结果封装</p>
 */
public class BlockHashQueryResp {

    /**
     * <p>查询的最新的blcokHash的相关参数</p>
     */
    public class BlockHashResp {
        @SerializedName(value = "latest_block_height")
        private Long latestBlockHeight;
        @SerializedName(value = "latest_block_hash")
        private String latestBlockHash;
        @SerializedName(value = "latest_block_time")
        private String latestBlockTime;

        public Long getLatestBlockHeight() {
            return latestBlockHeight;
        }

        public void setLatestBlockHeight(Long latestBlockHeight) {
            this.latestBlockHeight = latestBlockHeight;
        }

        public String getLatestBlockHash() {
            return latestBlockHash;
        }

        public void setLatestBlockHash(String latestBlockHash) {
            this.latestBlockHash = latestBlockHash;
        }

        public String getLatestBlockTime() {
            return latestBlockTime;
        }

        public void setLatestBlockTime(String latestBlockTime) {
            this.latestBlockTime = latestBlockTime;
        }
    }

    private String code;
    private String msg;
    private BlockHashResp data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BlockHashResp getData() {
        return data;
    }

    public void setData(BlockHashResp data) {
        this.data = data;
    }

    public String toJson() {
        return GsonUtil.getInstance().toJson(this);
    }
}
