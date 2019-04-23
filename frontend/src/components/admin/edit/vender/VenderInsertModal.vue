<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent medium">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">벤더사 등록</strong>
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
                                                <input type="text" class="inpText" name="vendername" v-model="vender.venderName">
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
                                                <input type="text" class="inpText" style="width:334px" :disabled="true" :value="sender.spidName + '(' + sender.spidEmail + ')'">
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
                        <a @click="save()" class="btns btnBlack">
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
import SenderSearchModal from '../sender/SenderSearchModal.vue';

export default {
    name: 'vender-insert-modal',

    data() {
        return {
            sender: {
                spidId: null,
                spidDept: null,
                spidName: null,
                spidTel1: null,
                spidTel2: null,
                spidEmail: null,
                job: null
            },

            vender: {
                venderName: null,
                spidId: null
            }
        }
    },

    methods: {
        searchSender: function () {
            this.$modal.show(
                SenderSearchModal, {
                    sender: this.sender
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
        },

        save: function () {
            if (this.vender.venderName !== null && this.vender.venderName.length > 0) {
                this.vender.spidId = this.sender.spidId;
                this.vender.spidName = this.sender.spidName;
                this.vender.spidEmail = this.sender.spidEmail;
                this.$store.dispatch('INSERT_VENDER', this.vender);
                this.$emit('close');
            } else {
                alert("벤더사 이름을 입력하세요.");
            }
        }
    }
}
</script>

<style scoped>

</style>