<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent small">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">도구가격 등록</strong>
                </div>
                <div class="popupCont">
                    <!-- 내용 : s -->
                    <div class="tblForm">
                        <table>
                            <caption></caption>
                            <colgroup>
                                <col style="width:117px">
                                <col style="width:auto">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>원가(EUR)</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="number" step="any" class="inpText" name="eur" v-model="tmpPrice.eur">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>환율</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="number" step="any" class="inpText" name="exchangeRate" v-model="tmpPrice.exchangeRate">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>원가(KRW)</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="number" step="any" class="inpText" name="krw" v-model="tmpPrice.krw">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>수량</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="number" class="inpText" name="quantity" v-model="tmpPrice.quantity">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>도구</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" style="width:134px" :disabled="true" v-model="tmpPrice.toolName">
                                            </span>
                                            <span>
                                                <a @click="searchTool()" class="btns btnLineGray">
                                                    <span>도구검색</span>
                                                </a>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="btnArea right btm">
                        <a @click="updatePrice()" class="btns btnBlack">
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
</template>

<script>
import ToolSearchModal from '../tool/ToolSearchModal.vue';
export default {
    name: 'price-update-modal',
    
    props: ['price'],

    data() {
        return {
            tmpPrice: {}
        }
    },

    created() {
        this.tmpPrice = {... this.price};
    },

    methods: {
        updatePrice: function () {
            this.$store.dispatch('UPDATE_PRICE', this.tmpPrice);
            this.$emit('close');
        },

        searchTool: function () {
            this.$modal.show(
                ToolSearchModal, {
                    tool: this.tmpPrice
                }, 
                {
			        width: "480px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'ToolSearchModal',
					clickToClose: false,
					transition: true
			    }
		    );
        }
    },

}
</script>

<style scoped>

</style>