<template>
    <!-- [레] 회원검색 : s -->
    <div class="popupWrap popFind">
        <div class="popupContent">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">고객사 검색</strong>
                </div>
                <div class="popupCont">
                    <!-- 내용 : s -->
                    <div class="topTitle">
                        <div class="titleBox">
                            <label class="mr10">고객검색</label>
                            <input id="searchInput" type="text" class="inpText mr10" style="width:200px" v-model="search" @keydown.enter="searchCustomer()">
                            <a class="btns btnBlack" @click="searchCustomer()"><span>검색</span></a>
                        </div>
                    </div>
                    <div class="tblData">
                        <table>
                            <caption>고객검색</caption>
                            <colgroup>
                                <col style="width:90px">
                                <col style="width:auto">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col"><span class="require">선택</span></th>
                                    <th scope="col">고객사</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="customer in customers" :key="customer.id" @click="selectCustomer(customer)"> 
                                    <td>
                                        <div class="radioWrap solo">
                                            <span>
                                                <input type="radio" class="inpRadio" name="rad009" :id="customer.id"> 
                                                <label :for="customer.id">
                                                    <span class="blind">해당항목 선택</span>
                                                </label>
                                            </span>
                                        </div>
                                    </td>
                                    <td class="textOverflow" :title="customer.customerCompany">{{customer.customerCompany + "(" + customer.customerName + ")"}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="btnArea right btm">
                        <a class="btns btnBlack" @click="save()">
                            <span>저장</span>
                        </a>
                        <a @click="$emit('close')" class="btns btnWhite">
                            <span>닫기</span>
                        </a>
                    </div>
                    <!-- 내용 : e -->
                </div>
            </div>
        </div>
    </div>
<!-- [레] 회원검색 : e -->
</template>
<script>
export default {
    name: 'customer-search-modal',

    props: ['customer', 'selectedTools'],

    data() {
        return {
            search: '',
            customers: [],
            tmpCustomer: {}
        }
    },

    methods: {
        searchCustomer: function() {
            if (this.search.length > 0) {

                let tool = []

                /*  */
                if (this.selectedTools) {
                    this.selectedTools.forEach(t => {
                        tool.push({toolName: t.toolName.replace(" Maintenance", ""), toolLicense: t.toolLicense});
                    });
                }

                axios.post('/api/customer', tool, {             
                    "params": {
                        search: this.search
                    }
                }).then((response) => {
                    this.customers = response.data;
                }).catch((error) => {
                    console.log(error);
                });
            }
        },

        selectCustomer: function (customer) {
            this.tmpCustomer = JSON.parse(JSON.stringify(customer));
        },

        save: function() {
            this.customer.customerId = this.tmpCustomer.customerId;
            this.customer.customerCompany = this.tmpCustomer.customerCompany;
            this.customer.customerName = this.tmpCustomer.customerName;
            this.customer.customerTel = this.tmpCustomer.customerTel;
            this.customer.customerEmail = this.tmpCustomer.customerEmail;

            this.customer.estimateModels = this.tmpCustomer.estimateModels;
            
            this.$emit('close');
        }
    },
}
</script>

<style scoped>

</style>