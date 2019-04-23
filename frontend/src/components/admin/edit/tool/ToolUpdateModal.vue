<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent medium">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">제품 수정</strong>
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
                                                <input type="text" class="inpText" name="toolName" v-model="tmpTool.toolName">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>모델번호</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="toolModelNumber" v-model="tmpTool.toolModelNumber">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>라이센스</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="toolLicense" v-model="tmpTool.toolLicense">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>상세정보</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="toolDetails" v-model="tmpTool.toolDetails">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>벤더사</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" style="width:334px" :disabled="true" v-model="tmpTool.venderName">
                                            </span>
                                            <span>
                                                <a @click="searchVender()" class="btns btnLineGray">
                                                    <span>벤더검색</span>
                                                </a>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="btnArea right btm">
                        <a @click="updateTool()" class="btns btnBlack">
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
import VenderSearchModal from '../vender/VenderSearchModal.vue';

export default {
    name: 'tool-update-modal',

    props: ['tool'],

    data() {
        return {
            tmpTool: {},
        }
    },

    created() {
        this.tmpTool = {... this.tool};
    },

    methods: {
        updateTool: function() {
            this.$store.dispatch('UPDATE_TOOL', this.tmpTool);
            this.$emit('close');
        },

        searchVender: function () {
            this.$modal.show(
                VenderSearchModal, {
                    vender: this.tmpTool
                }, 
                {
			        width: "480px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'VenderSearchModal',
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