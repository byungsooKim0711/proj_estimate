<template>
    <div>
        <!-- 타이틀 -->
        <div class="title">
            견 적 서
        </div>

        <!-- E-Number -->
        <div class="enumber">
            E-Number: {{estimate.estimateDate | moment('YYYY-MM-DD')}}{{"-" +estimate.estimateId}}
        </div>

        <!-- 수신인 -->
        <div class="recipient">
            <table class="type08">
                <colgroup>
                    <col style="width:auto">
                    <col style="width:150px">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col" rowspan="4" class="gray">수신</th>
                        <th scope="col">{{estimate.customerCompany}}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row" rowspan="3"></th>
                        <td>{{estimate.customerName}}</td>
                    </tr>
                    <tr>
                        <td>{{estimate.customerTel}}</td>
                    </tr>
                    <tr>
                        <td>{{estimate.customerEmail}}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 발신인 -->
        <div class="caller">
            <table class="type08">
                <colgroup>
                    <col style="width:auto">
                    <col style="width:150px">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col" class="gray">발신</th>
                        <th scope="col">(주) 에스피아이디</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row" rowspan="3"></th>
                        <td>{{estimate.spidDept}} / {{estimate.spidName}} {{estimate.job}}</td>
                    </tr>
                    <tr>
                        <td v-if="estimate.spidTel1 != null">{{estimate.spidTel1}} / {{estimate.spidTel2}}</td>
                        <td v-else>{{estimate.spidTel2}}</td>
                    </tr>
                    <tr>
                        <td>{{estimate.spidEmail}}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 제목, 견적금액 -->
        <div class="amount">
            <table class="type08">
                <tbody>
                    <tr>
                        <th scope="row">제목</th>
                        <td>{{estimate.title}}</td>
                    </tr>
                    <tr>
                        <th scope="row">견적금액(VAT별도)</th>
                        <td>{{estimate.estimatePrice | number2Kor}} ({{estimate.estimatePrice | priceWithCommas}})</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 단위: 원 -->
        <div class="unit">
            단위: 원
        </div>

        <!-- 항목 리스트, 총 합계 금액 -->
        <div class="sum">
            <table class="final">
                <colgroup>
                    <col style="width:35px">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:90px">
                    <col style="width:40px">
                    <col style="width:95px">
                </colgroup>
                <thead>
                    <tr class="center">
                        <th scope="col">No</th>
                        <th scope="col">품 목</th>
                        <th scope="col">단 위</th>
                        <th scope="col">상 세 내 역</th>
                        <th scope="col">단 가</th>
                        <th scope="col">수 량</th>
                        <th scope="col">합 계</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(detail, index) in estimateDetails" :key="detail.id">
                        <td class="center" scope="row">{{index+1}}</td>
                        <td class="center" v-if="detail.toolModelNumber != null">{{detail.toolName}} {{"(" + detail.toolModelNumber + ")"}}</td>
                        <td class="center" v-else>{{detail.toolName}}</td>
                        <td class="center">{{detail.toolLicense}}</td>
                        <td>{{detail.estimateDetails}}</td>
                        <td class="center">{{detail.unitPrice | priceWithCommas}}</td>
                        <td class="center">{{detail.quantity}}</td>
                        <td class="right">{{detail.supplyPrice | priceWithCommas}}</td>
                    </tr>
                    <tr class="backgry">
                        <td class="center bold" colspan="6">제안가 (할인적용, VAT 별도)</td>                        
                        <td class="right bold">{{estimate.estimatePrice | priceWithCommas}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <!-- 비고 내용들, 구매 혜택 내용들  -->
        <div class="note"  v-html="estimate.estimateNote">
        </div>

        <!-- 견적날짜(생성날짜) -->
        <div class="edate">
            {{estimate.estimateDate | moment('YYYY MMMM Do dddd')}}
        </div>

        <!-- 회사명 -->
        <div class="companyinfo">
            <div class="name">
                (주) 에스피아이디
            </div>

            <!-- 회사 위치 -->
            <div class="location">
                서울시 금천구 가산디지털1로 145, 1803호 (가산동, 에이스하이엔드타워 3차)
            </div>

            <!-- 대표님 성함 -->
            <div class="captain">
                대표이사&nbsp;&nbsp;&nbsp;이 승 주
            </div>

        </div>
        <!-- 대표님 직인 -->
        <!-- <span class="seal"> 
        </span> -->
    </div>
</template>

<script>
export default {
    name: 'admin-estimate-modal',

    props: ['estimate'],

    data() {
        return {
            estimateDetails: []
        }
    },

    filters: {
        priceWithCommas: function (price) {
            return "￦" + Math.floor(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        number2Kor : function( strNumber ) { 
            strNumber = Math.floor(strNumber).toString();
            strNumber = strNumber.replace(new RegExp(",", "g"), "");

            var arrayAmt = new Array("일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십"); 
            var arraypos = new Array("", "십", "백", "천"); 
            var arrayUnit = new Array("", "만", "억", "조", "경", "해");
 
            var pos = strNumber.length%4; 
            var len = (strNumber.length/4).toString();
            if( len.indexOf(".") > 0 )  {
                var unit = len.substring(0, len.indexOf("."));
            }
            else {
                var unit = strNumber.length/4-1;
            }

            var korNumber = "일금 "; 
            var op = 0;

            for(let i=0; i<strNumber.length; i++ ) { 
                if(pos==0) {
                    pos=4;
                } 
                var num = parseInt( strNumber.substring( i, i+1 ) ); 
                if( num != 0 ) { 
                    korNumber += arrayAmt[ num-1 ]; 
                    korNumber += arraypos[ pos-1 ]; 
                    op=1; 
                } 
                if(pos == 1) { 
                    if(op == 1) {
                        korNumber += arrayUnit[unit]; 
                    } 
                    unit--; 
                    op = 0; 
                } 
                pos--; 
            }

            if (korNumber.length == 0 || korNumber.length == null) {
                return  ""; 
            }
            else {
                return korNumber + "원정" ; 
            } 
        } 
    },

    methods: {
        getEstimateDetails: function () {
            axios.get('/admin/estimate/' + this.estimate.estimateId + '/estimateDetail', {

            }).then((response) => {
                this.estimateDetails = response.data;
            }).catch((error) => {
                console.log(error);
            });
        }
    },

    mounted() {
        this.getEstimateDetails();
    },
}
</script>

<style scoped>
.title {
    font-size: 35px;
    text-align: center;
    text-decoration-line: underline;
    font-weight: bold;
    padding-top: 60px;
}
.enumber {
    padding-left: 16px;
    padding-right: 16px;
    font-weight: bold;
    font-size: 15px;
}

/* 수신자 정보 */
.recipient {
    float: left;
}
.recipient .type08 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin: 20px 10px;
    width: 250px;
}
.recipient .type08 thead th {
    padding-left: 10px;
}
.recipient .type08 tbody th {
    width: 25px;
    padding: 10px;
    vertical-align: top;
}
.recipient .type08 td {
    width: auto;
    padding-left: 10px;
    padding-top: 2px;
    vertical-align: top;
}
.gray {
    background-color: gray;
    color: white;
}

/* 발신자 정보 */
.caller {
    float: right;
}

.caller .type08 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin: 20px 10px;
    width: 250px;
}

.caller .type08 thead th {
    padding-right: 10px;
}
.caller .type08 tbody th {
    width: 25px;
    padding: 10px;
    vertical-align: top;

}
.caller .type08 td {
    width: auto;
    padding-right: 10px;
    padding-top: 2px;
    vertical-align: top;
}

.gray {
    background-color: gray;
    color: white;
    text-align: center;
}

/* 제목, 견적금액 */
.amount .type08 {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin: 20px 10px;
    border: 2px solid black;
    width: 780px;
}
.amount .type08 thead th {
    padding: 10px;
    font-weight: bold;
}
.amount .type08 tbody th {
    width: 60px;
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border-bottom: 2px solid black;
}
.amount .type08 td {
    width: 350px;
    padding: 10px;
    vertical-align: top;
    border-bottom: 2px solid black;
    font-weight: bold;
}

.sum .final {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    margin: 20px 10px;
    width: 780px;
}

.unit {
    float: right;
    right: 5px;
    position: relative;
}

/* 합계 제안가 */
.sum .final thead th {
    padding: 5px;
    font-weight: bold;
    border: 1px solid black;
}
.sum .final tbody th {
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border: 1px solid black;
}
.sum .final td {
    padding: 8px;
    border: 1px solid black;
}


.note {
    padding: 15px;
}
.companyinfo {
    text-align: center;
}
.companyinfo .name {
    font-weight: bold;
    font-size: 25px;
}
.companyinfo .location {
    font-size: 15px;
    padding: 15px;
}
.companyinfo .captain {
    font-size: 15px;
    /* padding-bottom: 150px; */
}

.edate {
    text-align: center;
}

.seal{
    width: 50px;
    height: 50px;
    /* background-size: 50px; */
    position: absolute;
    bottom: 0px;
    right: 250px;
    background:url(../../../../assets/icon/이승주_대표님_직인1.png) no-repeat 50%;
}
.backgry {
    background-color: #f0f0f0;
}
.center {
    text-align: center;
}
.right {
    text-align: right;
}
.bold {
    font-weight: bold;
}
</style>