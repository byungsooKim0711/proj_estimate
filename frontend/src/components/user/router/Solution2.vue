<template>
    <div class="container">
        <div id="content">
            <div class="fullCont">
                <div class="topCont">
                    <div class="sort center">
                        <div>
                            solution 2
                            <select v-model="senderName">
                                <option>원기덕</option>
                            </select>
                        </div>

                        <!-- Vender 리스트 -->
                        <div>
                            벤더사: 
                            <select v-model="venderName">
                                <option v-for="vender in venderList" :key="vender.id">{{vender.venderName}}</option>
                            </select>
                        </div>

                        <!-- 제품군 이름 -->
                        <div v-show="venderName !== ''">
                            제품군: 
                            <select v-model="toolName">
                                <option v-for="name in toolNameList" :key="name.index"> {{name}}</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>           

            <!-- 제품군 별 제품들 -->
            <div class="serviceWrap" v-show="originToolList.length > 0 && toolName != ''">
                <div class="serviceCont side">
                    <h2 class="tit">도구</h2>
                    <div class="tblData list">
                        <table>
                            <thead>
                                <tr>
                                    <th>도구이름</th>
                                    <th>모델번호</th>
                                    <th>라이센스</th>
                                    <th>상세정보</th>
                                    <th>가격</th>
                                    <th>작업</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="tool in conditionalToolList" :key="tool.id">
                                    <td>{{tool.toolName}}</td>
                                    <td>{{tool.toolModelNumber}}</td>
                                    <td>{{tool.toolLicense}}</td>
                                    <td>{{tool.toolDetails}}</td>
                                    <td>{{tool.priceList[0].krw | priceWithCommas}}</td>
                                    <td>
                                        <em class="btn txtGreen02" @click="selectTool(tool)">추가</em>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            

            <!-- 선택된 도구들 -->
            <div class="serviceWrap" v-show="selectTools.length > 0">
                <div class="serviceCont side">
                    <div class="">
                        <h2 class="tit">추가된 품목</h2>
                        <p class="countMsg">  <em class="count txtBlue"></em> </p>
                        <div class="btnArea right">
                            <a class="btns btnLineGray txtBlue" @click="showEstimate()">
                                <span>견적내기</span>
                            </a>
                        </div>

                        <div class="tblData list">
                            <table>
                                <thead>
                                    <tr>
                                        <th>도구이름</th>
                                        <th>모델번호</th>
                                        <th>라이센스</th>
                                        <th>상세정보</th>
                                        <th>가격</th>
                                        <th>수량</th>
                                        <th>할인율(%)</th>
                                        <th>제안가</th>
                                        <th>작업</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(tool, index) in selectTools" :key="tool.id">
                                        <td>{{tool.toolName}}</td>
                                        <td>{{tool.toolModelNumber}}</td>
                                        <td>{{tool.toolLicense}}</td>
                                        <td>{{tool.toolDetails}}</td>
                                        <td>{{tool.priceList[0].krw | priceWithCommas}}</td>
                                        <td><input type="number" min="1" v-model="tool.quantity" @keydown.enter="calc(tool)"/></td>
                                        <td><input type="number" min="0" max="100" v-model="tool.rate" @keydown.enter="calc(tool)"/></td>
                                        <td>{{tool.suggestPrice | priceWithCommas}}</td>
                                        <td>
                                            <em class="btn txtRed" @click="deleteTool(index)">삭제</em>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Estimatemodal from './EstimateModal.vue';

export default {
    name: 'solution2',

    data() {
        return {
            senderName: '원기덕',
            venderName: '',
            venderList: [],
            originToolList: [], // 가격포함
            conditionalToolList: [],
            toolNameList: [], // doors, rhapsody, rpe, ...
            sender: {},

            toolName: '',

            selectTools: [],

            estimateModel: {
                estimateId: '',
                title: '',
                estimatePrice: 0,
                company: '',
                incharge:'',
                tel: '',
                email: '',
                spidId: '',
                estimateDate: new Date(),
                estimateNote: '' 
            }
        }
    },

    filters: {
        priceWithCommas: function (price) {
            return Math.floor(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
        },
    },

    watch: {
        senderName: function () {
            this.getSenderInfo();
        },

        venderName: function () {
            this.toolName = '';
            this.getToolList(this.venderList.find(v => v.venderName === this.venderName));
        },

        toolName: function () {
            this.conditionalToolList = this.originToolList.filter(tool => tool.toolName === this.toolName);
        }
    },

    methods: {
        getSenderInfo: function () {
            axios.get('/api/getSender/' + this.senderName, {
                "headers": {
                    'Content-Type': 'application/JSON; charset=UTF-8'
                }
            }).then((response) => {
                this.sender = response.data;
                this.venderList = this.sender.venders;
            }).catch((error) => {
                console.log(error);
            });
        },

        getToolList: function (vender) {
            axios.get('/api/getToolList/' + vender.venderId, {

            }).then((response) => {
                this.originToolList = response.data;
                let tmp = [];
                this.originToolList.forEach(tool => {
                    tool.checked = false;
                    tmp.push(tool.toolName);
                })
                this.toolNameList = tmp.reduce((a, b) => {
                    if (a.indexOf(b) < 0) {
                        a.push(b);
                    }
                    return a;
                }, []);

            }).catch((error) => {
                console.log(error);
            });
        },

        selectTool: function (tool) {
            if (tool.checked == false) {
                tool.quantity = 1;
                tool.rate = 25;
                tool.suggestPrice = (tool.quantity * (1-tool.rate/100) * tool.priceList[0].krw);
                this.selectTools.push(tool);
                tool.checked = !tool.checked;
            }
        },

        /* 함수 테스트 해야됌 ^^ */
        deleteTool: function (index) {
            if(confirm("모델번호 " + this.selectTools[index].toolModelNumber + "를 삭제하시겠습니까?")) {
                if (this.selectTools[index].checked == true) {
                    this.selectTools[index].checked = !this.selectTools[index].checked;
                    this.selectTools.splice(index, 1);
                }
            }
        },

        /* 견적내기 (견적서 포맷 나옴) */
        showEstimate: function () {

            this.estimateModel.spidId = this.sender.spidId;
            this.estimateModel.estimatePrice = 0;

            this.$modal.show(
                Estimatemodal, {
                    selectedTools: this.selectTools,
                    sender: this.sender,
                    estimateModel: this.estimateModel
                }, 
                {
			        width: "794px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'show-estimate',
					clickToClose: false,
					transition:true
			    }
		    );
        },

        calc: function (tool) {
            /* 분명 좋은 방법이 아니니까 나중에 다시 변경하도록 ! */
            tool.suggestPrice = (tool.quantity * (1-tool.rate/100) * tool.priceList[0].krw);
            this.selectTools.splice(
                  this.selectTools.findIndex(t => t.toolId == tool.toolId) // 시작위치
                , 1 // 지울 갯수
                , tool // 채울 것
            );
        }
    },

    created() {
        this.getSenderInfo();
    }
}
</script>

<style scoped>

</style>