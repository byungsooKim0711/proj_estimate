<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">도구가격 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue">{{prices.length}}</em> 건의 벤더가 검색되었습니다.</p>
							<div class="btnArea right">
								<span class="selectboxWrap mr10" style="width:130px">
									<select>
										<option></option>
									</select>
								</span>
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<a class="btns btnLineGray txtBlue" @click="insertPrice()">
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
										<col style="width:150px">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">도구이름</th>
											<th scope="col">라이센스</th>
											<th scope="col">원가(EUR)</th>
											<th scope="col">환율</th>
                                            <th scope="col">원가(KRW)</th>
                                            <th scope="col">수량</th>
                                            <th scope="col">작업</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="price in prices" :key="price.id" >
											<td>{{price.toolName}}</td>
											<td>{{price.toolLicense}}</td>   
                                            <td>{{price.eur | priceWithCommas}}</td>
                                            <td>{{price.exchangeRate | priceWithCommas}}</td>
                                            <td>{{price.krw | priceWithCommas}}원</td>
                                            <td>{{price.quantity + "개"}}</td>
                                            <td>
                                                <em @click="updatePrice(price)" class="btn txtGreen02">수정</em>
                                                <em @click="deletePrice(price)" class="btn txtRed">삭제</em>
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
import PriceInsertModal from '../edit/price/PriceInsertModal.vue';
import PriceUpdateModal from '../edit/price/PriceUpdateModal.vue';

export default {
    name: 'admin-price',

    data() {
        return {
            search: '',
			_timer: 5000,
			
        }
    },

    computed: {
        ...mapGetters({
            prices: 'getPrices'
        })
    },

    filters: {
        priceWithCommas: function (price) {
			if (price) {
				return Math.floor(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}
		}
    },

    methods: {
        getPrices: function () {
			this.$store.dispatch('LOAD_PRICES', this.search);
        },

		insertPrice: function() {
			this.$modal.show(
                PriceInsertModal, {
                    text: 'text'
                }, 
                {
			        width: "400px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'PriceInsertModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		updatePrice: function(price) {
			this.$modal.show(
                PriceUpdateModal, {
                    price: price
                }, 
                {
			        width: "400px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'PriceUpdateModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		deletePrice: function(price) {
			if (confirm(price.toolName + "(" + price.toolLicense + ")" + " 의 가격을 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_PRICE', price);
			}
		},

		handleChange: function () {
			clearTimeout(this._timer);
			this._timer = window.setTimeout(() => {
				this.getPrices();
			}, 500);
		},
    },

    mounted() {
        this.getPrices();
    },
}
</script>

<style scoped>

</style>