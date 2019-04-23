<template>
    <div id="pdf">
        <!-- 타이틀 -->
        <div class="title">
            견 적 서
        </div>

        <!-- E-Number -->
        <div class="enumber">
            E-Number: ... 고민중 ...
        </div>

        <!-- 수신인 -->
        <div class="recipient">
            <table class="type08" v-on:dblclick="modifyRecipient">
                <thead>
                    <tr>
                        <th scope="cols" rowspan="4" class="gray">수신</th>
                        <th scope="cols">{{estimate.company}}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row" rowspan="3"></th>
                        <td>{{estimate.incharge}}</td>
                    </tr>
                    <tr>
                        <td>{{estimate.tel}}</td>
                    </tr>
                    <tr>
                        <td>{{estimate.email}}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 발신인 -->
        <div class="caller">
            <table class="type08">
                <thead>
                    <tr>
                        <th scope="cols" class="gray">발신</th>
                        <th scope="cols">(주) 에스피아이디</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row" rowspan="3"></th>
                        <td>{{sender.spidDept}} / {{sender.spidName}} {{sender.job}}</td>
                    </tr>
                    <tr>
                        <td v-if="sender.spidTel1 != null">{{sender.spidTel1}} / {{sender.spidTel2}}</td>
                        <td v-else>{{sender.spidTel2}}</td>
                    </tr>
                    <tr>
                        <td>{{sender.spidEmail}}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- 제목, 견적금액 -->
        <div class="amount">
            <table class="type08" @dblclick="modifyTitle">
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
            <table class="type08">
                <colgroup>
                    <col style="width:30px">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:auto">
                    <col style="width:200px">
                </colgroup>
                <thead>
                    <tr class="center">
                        <th scope="col">No</th>
                        <th scope="col">품목</th>
                        <th scope="col">단위</th>
                        <th scope="col">상세내역</th>
                        <th scope="col">단가</th>
                        <th scope="col">수량</th>
                        <th scope="col">합계</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(tool, index) in selectedTools" :key="tool.id">
                        <td class="center" scope="row">{{index+1}}</td>
                        <td class="center" v-if="tool.toolModelNumber != null">{{tool.toolName}} {{"(" + tool.toolModelNumber + ")"}}</td>
                        <td class="center" v-else>{{tool.toolName}}</td>
                        <td class="center">{{tool.toolLicense}}</td>
                        <td>{{tool.toolDetails}}</td>
                        <td class="center">{{tool.priceList[0].krw | priceWithCommas}}</td>
                        <td class="center">{{tool.quantity}}</td>
                        <td class="right">{{tool.suggestPrice | priceWithCommas}}</td>
                    </tr>
                    <tr class="backgry">
                        <td class="center bold" colspan="6">제안가 (할인적용, VAT 별도)</td>                        
                        <td class="right bold">{{estimate.estimatePrice | priceWithCommas}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <!-- 비고 내용들, 구매 혜택 내용들  -->
        <div class="note" @dblclick="modifyNote" v-html="estimate.estimateNote">
        </div>

        <!-- 견적날짜(생성날짜) -->
        <div class="edate">
            {{estimate.estimateDate}}
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

            <!-- 대표님 직인 -->
            <span class="seal"> 
            </span>
        </div>

        <!-- 저장, 취소, PDF로 내보내기 -->
        <div class="btnArea right btm" data-html2canvas-ignore="true">
            <button @click="makePDF('#pdf')" class="btns btnBlue">
                <span>PDF로 내보내기</span>
            </button>
            <a @click="$emit('close')" class="btns btnBlack">
                <span>저장</span>
            </a>
            <a @click="$emit('close')" class="btns btnWhite">
                <span>닫기</span>
            </a>
        </div>
    </div>
</template>

<script>
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

import RecipientEditModal from '../edit/RecipientEditModal.vue';
import TitleEditModal from '../edit/TitleEditModal.vue';
import NoteEditModal from '../edit/NoteEditModal.vue';

export default {
    name: 'estimate-modal',

    props: ['selectedTools', 'sender', 'estimate'],

    data() {
        return {
            propTitle: 'MY_PDF',
        }
    },

    mounted() {
        this.selectedTools.forEach(t => {
            this.estimate.estimatePrice += t.suggestPrice;
        })
    },

    methods: {
        /* PDF로 내보내기 */
        makePDF: function (selector) { 

            /* 파일 이름 */
            this.propTitle = new Date().toISOString().slice(0,10).replace(/-/g,"") + "_" + this.estimate.title;

			window.html2canvas = html2canvas 
			let that = this
			let pdf = new jsPDF('p', 'mm', 'a4')
			let canvas = pdf.canvas
			const pageWidth = 210 //캔버스 너비 mm
			const pageHeight = 295 //캔버스 높이 mm
			canvas.width = pageWidth
            let ele = document.querySelector(selector)
			let width = ele.offsetWidth 
			let height = ele.offsetHeight 
			let imgHeight = pageWidth * height/width 
			if(!ele){
				console.warn(selector + ' is not exist.')
				return false
            }
            html2canvas(ele, {
                scale: 4 /* 선명도 옵션 */
            }).then(canvas => {
                let position = 0
                    const imgData = canvas.toDataURL('image/png', 20);
					pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight, undefined, 'slow')
					//Paging 처리
					let heightLeft = imgHeight
					heightLeft -= pageHeight
					while (heightLeft >= 0) {
						position = heightLeft - imgHeight
						pdf.addPage();
						pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight)
						heightLeft -= pageHeight
					}
					pdf.save(that.propTitle.toLowerCase() + '.pdf')
            });
        },

        /* 수신인 수정 */
        modifyRecipient: function () {
            this.$modal.show(
                RecipientEditModal, {
                    estimate: this.estimate
                }, 
                {
			        width: "750px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'RecipientEditModal',
					clickToClose: false,
                    transition: true,
                    'before-close': this.beforeClose
                }
		    );
        },

        /* 제목 수정 */
        modifyTitle: function () {
            this.$modal.show(
                TitleEditModal, {
                    estimate: this.estimate
                }, 
                {
			        width: "750px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'TitleEditModal',
					clickToClose: false,
                    transition: true
                }
		    );
        },

        /* 비고 수정 */
        modifyNote: function () {
            this.$modal.show(
                NoteEditModal, {
                    estimate: this.estimate
                }, 
                {
			        width: "750px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'NoteEditModal',
					clickToClose: false,
                    transition: true
                }
		    );
        },

        beforeClose: function(event) {
            // console.log(event);
        }
    },

    filters: {
        /* 19940711 -> 19,940,711 */
        priceWithCommas: function (price) {
            return "￦" + Math.floor(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            /* let parts = price.toString().split(".");
            return "￦" + parts[0].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1]: ""); */
        },

        /* 19,940,711 -> 일금 일천구백구십사만칠백십일원정 */
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
    }
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
.recipient:hover {
    border: 4px solid cornflowerblue;
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
.amount .type08:hover {
    border: 4px solid cornflowerblue;
}
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

.sum .type08 {
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
.sum .type08 thead th {
    padding: 5px;
    font-weight: bold;
    border: 1px solid black;
}
.sum .type08 tbody th {
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border: 1px solid black;
}
.sum .type08 td {
    width: 350px;
    padding: 10px;
    vertical-align: top;
    border: 1px solid black;
}


.note {
    padding: 15px;
}
.note:hover {
    border: 4px solid cornflowerblue;
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
    width: 80px;
    height: 80px;
    background-size: 75px;
    position: absolute;
    bottom: -20px;
    right: 230px;
    background:url(../../../assets/icon/이승주_대표님_직인.png) no-repeat 50%;
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