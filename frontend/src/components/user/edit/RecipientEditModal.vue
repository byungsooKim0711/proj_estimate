<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent small">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">고객사</strong>
                </div>
                <div class="popupCont">
                    <!-- 내용 : s -->
                    <div class="tblForm">
                        <table>
                            <tbody>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>회사명</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidDept" placeholder="회사명을 입력하세요." v-model="tmpCustomer.customerCompany">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>담당자</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidName" placeholder="담당자를 입력하세요." v-model="tmpCustomer.customerName">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>전화번호</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidTel1" placeholder="전화번호를 입력하세요." v-model="tmpCustomer.customerTel">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>이메일</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidTel2" placeholder="이메일을 입력하세요." v-model="tmpCustomer.customerEmail">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- <div class="btnArea right btm">
                        <span>
                            <a class="btns btnLineGray" @click="searchCustomer()" style="width: 340px">
                                <span>고객사 검색</span>
                            </a>
                        </span>
                    </div> -->
                    <div class="btnArea right btm">
                        <a @click="save()" class="btns btnBlack">
                            <span>저장</span>
                        </a>
                        <a @click="close()" class="btns btnWhite">
                            <span>닫기</span>
                        </a>
                    </div>
                    <!-- 내용 : e -->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CustomerSearchModal from '../edit/CustomerSearchModal.vue';

export default {
    name: 'recipient-edit-modal',

    props: ['customer'],

    data() {
        return {
            tmpCustomer: {...this.customer}
        }
    },

    created() {

    },

    methods: {

        /* 수신자 정보 저장 */
        save: function() {
            this.customer.customerId = this.tmpCustomer.customerId;
            this.customer.customerCompany = this.tmpCustomer.customerCompany;
            this.customer.customerName = this.tmpCustomer.customerName;
            this.customer.customerTel =  this.tmpCustomer.customerTel;
            this.customer.customerEmail = this.tmpCustomer.customerEmail;
            this.$emit('close');
        },

        searchCustomer: function() {
             this.$modal.show(
                CustomerSearchModal, {
                    customer: this.tmpCustomer
                }, 
                {
			        width: "480px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'CustomerSearchModal',
					clickToClose: false,
					transition:true
			    }
		    );
        },

        close: function() {
            this.customer.customerId = null;
            this.customer.customerCompany = null;
            this.customer.customerName = null;
            this.customer.customerTel = null;
            this.customer.customerEmail = null;
            
            this.$emit('close');
        }
    }

}
</script>

<style scoped>
.recipientedit {
    position: relative;
    margin: 5px 5px 20px 5px;
}
th {
    font-size: 20px;
    text-align: center;
}
</style>