<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent medium">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">벤더사 수정</strong>
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
                                        <span>이름</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="vendername" v-model="tmpVender.venderName">
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
                                                <input type="text" class="inpText" style="width:334px" :disabled="true" :value="tmpVender.spidName + '(' + tmpVender.spidEmail + ')'">
                                            </span>
                                            <span>
                                                <a class="btns btnLineGray" @click="searchSender()">
                                                    <span>회원검색</span>
                                                </a>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="btnArea right btm">
                        <a @click="updateVender()" class="btns btnBlack">
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
import SenderSearchModal from "../sender/SenderSearchModal.vue";

export default {
    name: 'vender-update-modal',
    
    props: ['vender'],

    data() {
        return {
            tmpVender: {}
        }
    },

    created() {
        this.tmpVender = {... this.vender};
    },

    methods: {
        updateVender: function() {
            this.$store.dispatch('UPDATE_VENDER', this.tmpVender);
            this.$emit('close');
        },

        searchSender: function() {
            this.$modal.show(
                SenderSearchModal, {
                    sender: this.tmpVender
                }, 
                {
			        width: "480px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'SenderSearchModal',
					clickToClose: false,
					transition:true
			    }
		    );
        }
    },
    
}
</script>

<style scoped>

</style>