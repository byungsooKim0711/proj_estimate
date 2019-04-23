<template>
    <div class="container">
        <div id="content">
            <div class="fullCont">
                <div class="topCont">
                    <div class="sort center">
                        <div>
                            solution 1
                            <select v-model="senderName">
                                <option>박창도</option>
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
                                    <!-- <th>모델번호</th> -->
                                    <th>라이센스</th>
                                    <!-- <th>상세정보</th> -->
                                    <th>가격</th>
                                    <th>작업</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="tool in conditionalToolList" :key="tool.id">
                                    <td>{{tool.toolName}}</td>
                                    <!-- <td>{{tool.toolModelNumber}}</td> -->
                                    <td>{{tool.toolLicense}}</td>
                                    <!-- <td>{{tool.toolDetails}}</td> -->
                                    <td><span v-for="price in tool.priceList" :key="price.id">{{price.quantity + "개: "}} {{price.krw | priceWithCommas}}&nbsp;&nbsp;</span></td>
                                    <td>
                                        <em class="btn txtGreen02" @click="selectTool(tool)">추가</em>
                                        <em class="btn txtBrown" @click="selectToolMaintenance(tool)">유지보수</em>
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
                                        <!-- <th>모델번호</th> -->
                                        <th>라이센스</th>
                                        <!-- <th>상세정보</th> -->
                                        <th>가격</th>
                                        <th>수량</th>
                                        <th>마진율(%)</th>
                                        <th>제안가</th>
                                        <th>작업</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(tool, index) in selectTools" :key="tool.id">
                                        <td>{{tool.toolName}}</td>
                                        <!-- <td>{{tool.toolModelNumber}}</td> -->
                                        <td>{{tool.toolLicense}}</td>
                                        <!-- <td>{{tool.toolDetails}}</td> -->
                                        <td>{{tool.priceList[0].krw | priceWithCommas}}</td>
                                        <td><input type="number" min="1" v-model.number="tool.quantity" @keydown.enter="calc(tool)"/></td>
                                        <td><input type="number" min="0" max="100" v-model="tool.profitRate" @keydown.enter="calc(tool)"/></td>
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
    name: 'solution1',
    
    data() {
        return {
            senderName: '박창도',
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
                    tool.checkedMaintenance = false;
                    this.venderList.forEach(vender => {
                        if (vender.venderId == tool.venderId) {
                            tool.venderName = vender.venderName;
                            tool.venderId = vender.venderId;
                        }
                    })
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

                if (tool.venderName === 'APIS') {
                    tool.profitRate = 35;
                    tool.suggestPrice = (tool.quantity * tool.priceList[0].eur * tool.priceList[0].exchangeRate * 1.05 * (1+tool.profitRate/100));
                } else if(tool.venderName === 'ISOGRAPH') {
                    tool.suggestPrice = (tool.quantity * tool.priceList[0].eur * tool.priceList[0].exchangeRate * 1.05);
                    tool.profitRate = 40
                }
                
                tool.suggestPrice = this.roundUp(tool.suggestPrice, 4);

                tool.checked = true;
                this.selectTools.push(tool);
            }
        },

        selectToolMaintenance: function (tool) {
            /* Deep copy는 비효율적이니 immutable update pattern을 사용 하라는 지적... (immutable update pattern 이란 ?) */
            let tmpTool = JSON.parse(JSON.stringify(tool));

            if (tool.checkedMaintenance == false) {
                tmpTool.quantity = 1;
                tmpTool.toolName += " Maintenance";

                if (tmpTool.venderName === 'APIS') {
                    /*
                        제품                   | 유지보수
                        IQ-FMEA, NLX IQ-FMEA      0.15
                        CARM server               0.23
                        CSS Module / CSA Modul    0.18 
                     */

                    tmpTool.profitRate = 35;
                    tmpTool.suggestPrice = (tmpTool.quantity * tmpTool.priceList[0].eur * tmpTool.priceList[0].exchangeRate * 1.05 * (1+tmpTool.profitRate/100));
                    if (tmpTool.toolName.includes("FMEA")) {
                        tmpTool.priceList[0].krw *= 0.15
                        tmpTool.suggestPrice *= 0.15;
                    } else if (tmpTool.toolName.includes("CARM")) {
                        tmpTool.priceList[0].krw *= 0.23
                        tmpTool.suggestPrice *= 0.23;
                    } else if (tmpTool.toolName.includes("CSS")) {
                        tmpTool.priceList[0].krw *= 0.18
                        tmpTool.suggestPrice *= 0.18
                    }
                } else if(tmpTool.venderName === 'ISOGRAPH') {
                    tmpTool.priceList[0].krw *= 0.15;
                    tmpTool.suggestPrice = (tmpTool.quantity * tmpTool.priceList[0].eur * tmpTool.priceList[0].exchangeRate * 1.05) * 0.15;
                    tmpTool.profitRate = 40
                } else if (tmpTool.venderName === 'OMNEX') {
                    console.log("OMNEX");
                }
                
                tmpTool.suggestPrice = this.roundUp(tmpTool.suggestPrice, 4);
                this.selectTools.push(tmpTool);
                tool.checkedMaintenance = true;
            }
        },

        /* 함수 테스트 해야됌 ^^ */
        deleteTool: function (index) {
            if(confirm(this.selectTools[index].toolName + "를 삭제하시겠습니까?")) {

                /* 삭제 할 대상이 유지보수인가 ? */
                if (this.selectTools[index].toolName.includes("Maintenance")) {

                    /* 원본을 찾아서 */
                    for(let i=0; i<this.originToolList.length; i++) {

                        /* 유지보수 체크 false */
                        if (this.originToolList[i].toolId === this.selectTools[index].toolId) {
                            this.originToolList[i].checkedMaintenance = false;
                            break;
                        }
                    }

                /* 삭제 할 대상이 유지보수가 아닐 때 */
                } else {
                    this.selectTools[index].checked = false;
                }

                /* 선택한 대상 리스트에서 제거 */
                this.selectTools.splice(index, 1);
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
                    estimate: this.estimateModel
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
            /* 1 미만의 값이 입력되었을 때 */
            if (tool.quantity <= 0) {
                alert("수량에 1 이상의 값을 입력하세요.");
                return ;
            }

            let venderName = tool.venderName;

            if (venderName === 'ISOGRAPH') {
                if (tool.quantity === 1) {
                    tool.suggestPrice = (tool.priceList[0].eur * tool.priceList[0].exchangeRate);
                } else if (tool.quantity === 2) {
                    /* { ( EUR * 환율 * 1.6 ) } */
                    tool.suggestPrice = (tool.priceList[0].eur * tool.priceList[0].exchangeRate) * 1.6;
                } else if (tool.quantity >= 3) {
                    /* { ( 0.4 * EUR * 환율 * (N+2) ) } */
                    tool.suggestPrice = (0.4 * tool.priceList[0].eur * tool.priceList[0].exchangeRate * (tool.quantity+2));
                }
            } else if (venderName === 'APIS') {
                if (tool.toolLicense.includes("Local")) {
                    tool.suggestPrice = (tool.quantity * tool.priceList[0].eur * tool.priceList[0].exchangeRate * (1+tool.profitRate/100));
                } else {
                    if(tool.quantity >= 1 && tool.quantity <= 5) {
                        tool.suggestPrice = (tool.priceList[tool.quantity-1].eur * tool.priceList[tool.quantity-1].exchangeRate * (1+tool.profitRate/100));
                    } else if (tool.quantity >= 6) {
                        let diff = tool.quantity - 5;
                        tool.suggestPrice = (tool.priceList[4].eur * tool.priceList[4].exchangeRate * (1+tool.profitRate/100));
                        tool.suggestPrice += (tool.priceList[5].eur * tool.priceList[4].exchangeRate * (1+tool.profitRate/100)) * diff;
                    }
                }
                    
            } else if (venderName === 'OMNEX') {
                console.log("OMNEX");
            }

            /* 유지보수 일 때 */
            /*
                제품                   | 유지보수
                IQ-FMEA, NLX IQ-FMEA      0.15
                CARM server               0.23
                CSS Module / CSA Modul    0.18 
            */
            if (tool.toolName.includes("Maintenance")) {
                if (tool.venderName === 'APIS') {
                    if (tool.toolName.includes("FMEA")) {
                        tool.suggestPrice *= 0.15;
                    } else if (tool.toolName.includes("CARM")) {
                        tool.suggestPrice *= 0.23;
                    } else if (tool.toolName.includes("CSS")) {
                        tool.suggestPrice *= 0.18
                    }		                    
                } else if (tool.venderName == 'ISOGRAPH') {
                    tool.suggestPrice *= 0.15;
                } else if (tool.venderName == 'OMNEX') {
    
                }
            }

            tool.suggestPrice = this.roundUp(tool.suggestPrice * 1.05, 4);

            /* 분명 좋은 방법이 아니니까 나중에 다시 변경하도록 ! */
            this.selectTools.splice(
                  this.selectTools.findIndex(t => t == tool  /* t.toolId == tool.toolId && t.toolName == tool.toolName */) // 시작위치
                , 1 // 지울 갯수
                , tool // 채울 것
            );
        },

        roundUp: function(number, digit) {
            let div = 1;
            for (let i=0; i<digit; i++) {
                div = div * 10;
            }
            
            return Math.ceil(number/div)*div;
        }
    },

    created() {
        this.getSenderInfo();
    }

}
</script>

<style scoped>
.info .type08 {
    float: right
}
.type08 th {
    text-align: right;
    width: 100px;
    padding-right: 20px;
}
</style>