<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">견적서 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue"></em> 건의 견적서가 검색되었습니다.</p>
							<div class="btnArea right">
								<span class="selectboxWrap mr10" style="width:130px">
									<select>
										<option></option>
									</select>
								</span>
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<a class="btns btnLineGray txtBlue" @click="insertEstimate()">
									<span>등록</span>
								</a>
							</div>
							<!-- tblData : s -->
							<div class="tblData list">
								<table>
									<colgroup>
										<col style="width:350px">
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
											<th scope="col">날짜</th>
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
											<td>{{estimate.estimateDate | moment('YYYY MMMM Do dddd')}}</td>
											<td>{{estimate.title}}</td>   
                                            <td>{{estimate.company}}</td>
                                            <td>{{estimate.incharge}}</td>
                                            <td>{{estimate.tel}}</td>
                                            <td>{{estimate.email}}</td>
                                            <td>{{estimate.estimateNote}}</td>
                                            <td>{{estimate.estimatePrice}}</td>
                                            <td>
                                                <em @click="updateEstimate(estimate)" class="btn txtGreen02">자세히보기</em>
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

export default {
    name: 'admin-estimate',

    computed: {
        ...mapGetters({
            estimates: 'getEstimates'
        })
    },

    data() {
        return {
            search: '',
            _timer: 5000
        }
    },

    methods: {
        getEstimates: function () {
            this.$store.dispatch('LOAD_ESTIMATES', this.search);
		},
		
		insertEstimate: function () {

		},

		updateEstimate: function (estimate) {

		},

		deleteEstimate: function (estimate) {
			if (confirm("정말 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_ESTIMATE', estimate);
			}
		},

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