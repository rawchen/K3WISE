/*
 * MIT License
 *
 * Copyright (c) 2022 Lark Technologies Pte. Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice, shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.lundong.k3wise.event;

import com.google.gson.annotations.SerializedName;
import com.lark.oapi.event.model.BaseEvent;

public class ApprovalInstanceStatusUpdatedEvent extends BaseEvent {

    @SerializedName("event")
    private ApprovalInstanceStatusUpdatedEventData event;

    public ApprovalInstanceStatusUpdatedEventData getEvent() {
        return event;
    }

    public void setEvent(ApprovalInstanceStatusUpdatedEventData event) {
        this.event = event;
    }

    public static class ApprovalInstanceStatusUpdatedEventData {

        @SerializedName("app_id")
        private String appId;
        @SerializedName("tenant_key")
        private String tenantKey;
        @SerializedName("approval_code")
        private String approvalCode;
        @SerializedName("instance_code")
        private String instanceCode;
        @SerializedName("status")
        private String status;
        @SerializedName("operate_time")
        private Long operateTime;
        @SerializedName("instance_operate_time")
        private String instanceOperateTime;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getTenantKey() {
            return tenantKey;
        }

        public void setTenantKey(String tenantKey) {
            this.tenantKey = tenantKey;
        }

        public String getApprovalCode() {
            return approvalCode;
        }

        public void setApprovalCode(String approvalCode) {
            this.approvalCode = approvalCode;
        }

        public String getInstanceCode() {
            return instanceCode;
        }

        public void setInstanceCode(String instanceCode) {
            this.instanceCode = instanceCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(Long operateTime) {
            this.operateTime = operateTime;
        }

        public String getInstanceOperateTime() {
            return instanceOperateTime;
        }

        public void setInstanceOperateTime(String instanceOperateTime) {
            this.instanceOperateTime = instanceOperateTime;
        }
    }
}
