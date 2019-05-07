<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">견적서 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue">{{estimates.length}}</em> 건의 견적서가 검색되었습니다.</p>
							<div class="btnArea right">
								<span class="inputboxWrap mr10" style="width:160px">
									<input v-model="from" type="date"/>
								</span>
								<span class="inputboxWrap mr10" style="width:160px">
									<input v-model="to" type="date"/>
								</span>
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<!-- <a class="btns btnLineGray txtBlue" @click="insertEstimate()">
									<span>등록</span>
								</a> -->
							</div>
							<!-- tblData : s -->
							<div class="tblData list">
								<table>
									<colgroup>
										<col style="width:80px">
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:auto">
                                        <col style="width:auto">
                                        <col style="width:auto">
										<col style="width:auto">
                                        <col style="width:auto">
										<col style="width:150px">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">발신인</th>
											<th scope="col">견적날짜</th>
											<th scope="col">제목</th>
											<th scope="col">회사</th>
											<th scope="col">담당자</th>
                                            <th scope="col">전화</th>
                                            <th scope="col">이메일</th>
                                            <th scope="col">비고</th>
                                            <th scope="col">가격</th>
                                            <th scope="col">작업</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="estimate in estimates" :key="estimate.id" >
											<td>{{estimate.spidName}}</td>
											<td>{{estimate.estimateDate | moment('YYYY년 MMMM Do dddd')}}</td>
											<td>{{estimate.title}}</td>   
                                            <td>{{estimate.customerCompany}}</td>
                                            <td>{{estimate.customerName}}</td>
                                            <td>{{estimate.customerTel}}</td>
                                            <td>{{estimate.customerEmail}}</td>
                                            <td>{{estimate.estimateNote}}</td>
                                            <td>{{estimate.estimatePrice | priceWithCommas()}}</td>
                                            <td>
                                                <em @click="showDetails(estimate)" class="btn txtGreen02">자세히보기</em>
                                                <!-- <em @click="deleteEstimate(estimate)" class="btn txtRed">삭제</em> -->
                                            </td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- tblData : e -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
import AdminEstimateModal from '../edit/estimate/AdminEstimateModal.vue';

import utilAlgorithm from '../../util/utilAlgorithm.js';

export default {
    name: 'admin-estimate',

    computed: {
        ...mapGetters({
            estimates: 'getEstimates'
        })
	},
	
	filters: {
		priceWithCommas: function (price) {
            return utilAlgorithm.utilAlgorithm.priceWithCommas(price) + "원";
        },
	},

	watch: {
		from: {
			handler: function (after, before) {
				if (after > this.to) {
					alert("잘못된 날짜를 입력했습니다.");
					this.from = before;
				}
				this.handleChange();
			}
		},

		to: {
			handler: function (after, before) {
				if (after < this.from) {
					alert("잘못된 날짜를 입력했습니다.");
					this.to = before;
				}
				this.handleChange();
			}
		}
	},

    data() {
        return {
            search: '',
			_timer: 5000,
			from: '2018-01-01',
			to: this.$moment(new Date()).format("YYYY-MM-DD")
        }
    },

    methods: {
        getEstimates: function () {
            this.$store.dispatch('LOAD_ESTIMATES', {search:this.search, from:this.from, to:this.to + " 23:59:59"});
		},
		
		showDetails: function (estimate) {
			this.$modal.show(
                AdminEstimateModal, {
                    estimate: estimate
                }, 
                {
			        width: "794px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'AdminEstimateModal',
					clickToClose: false,
					transition: true
			    }
		    );
		},

		deleteEstimate: function (estimate) {
			if (confirm("정말 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_ESTIMATE', estimate);
			}
		},

		insertEstimate: function () {},
		updateEstimate: function (estimate) {},

		handleChange: function () {
			clearTimeout(this._timer);
			this._timer = window.setTimeout(() => {
				this.getEstimates();
			}, 500);
		}
    },

    mounted() {
        this.getEstimates();
    }

}
</script>

<style scoped>

</style>