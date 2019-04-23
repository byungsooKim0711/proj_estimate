<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent medium">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">제품 등록</strong>
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
                                                <input type="text" class="inpText" name="toolName" v-model="tool.toolName">
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
                                                <input type="text" class="inpText" name="toolModelNumber" v-model="tool.toolModelNumber">
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
                                                <input type="text" class="inpText" name="toolLicense" v-model="tool.toolLicense">
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
                                                <input type="text" class="inpText" name="toolDetails" v-model="tool.toolDetails">
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
                                                <input type="text" class="inpText" style="width:334px" :disabled="true" v-model="vender.venderName">
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
import VenderSearchModal from '../vender/VenderSearchModal.vue';

export default {
    name: 'tool-insert-modal',

    data() {
        return {
            tool: {
                toolName: null,
                toolModelNumber: null,
                toolLicense: null,
                toolDetails: null,
                venderId: null,
            },

            vender: {
                venderId: null,
                venderName: null,
                spidId: ''
            }
        }
    },

    methods: {
        save: function () {
            if ((this.tool.toolName !== null && this.tool.toolName.length > 0) && (this.tool.toolLicense !== null && this.tool.toolLicense.length > 0)) {
                this.tool.venderId = this.vender.venderId;
                this.tool.venderName = this.vender.venderName;
                this.$store.dispatch('INSERT_TOOL', this.tool);
                this.$emit('close');
            } else {
                alert("도구 이름과 라이센스를 입력하세요.");
            }
        },

        searchVender: function () {
            this.$modal.show(
                VenderSearchModal, {
                    vender: this.vender
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