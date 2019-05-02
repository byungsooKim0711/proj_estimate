<template>
	<!-- [레] 회원검색 : s -->
	<div class="popupWrap popFind">
		<div class="popupContent">
			<div class="popup">
				<div class="popupHead">
					<strong class="tit">회원검색</strong>
				</div>
				<div class="popupCont">
					<!-- 내용 : s -->
					<div class="topTitle">
						<div class="titleBox">
							<label class="mr10">회원검색</label>
							<input id="searchInput" type="text" class="inpText mr10" style="width:200px" v-model="search" @keydown.enter="searchSender()">
							<a class="btns btnBlack" @click="searchSender()"><span>검색</span></a>
						</div>
					</div>
					<div class="tblData">
						<table>
							<caption>회원검색</caption>
							<colgroup>
								<col style="width:90px">
								<col style="width:auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col"><span class="require">선택</span></th>
									<th scope="col">회원</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="sender in senders" :key="sender.id" @click="selectSender(sender)"> 
									<td>
										<div class="radioWrap solo">
											<span>
												<input type="radio" class="inpRadio" name="rad009" :id="sender.id"> 
												<label :for="sender.id">
													<span class="blind">해당항목 선택</span>
												</label>
											</span>
										</div>
									</td>
									<td class="textOverflow" :title="sender.spidName">{{sender.spidName}}({{sender.spidEmail}})</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="btnArea right btm">
						<a class="btns btnBlack" @click="save()">
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
	<!-- [레] 회원검색 : e -->
</template>

<script>
export default {
    name: 'sender-search-modal',

    props: ['sender'],

    data() {
        return {
            search: '',
            senders: [],
            tmpSender: {}
        }
    },

    methods: {
        searchSender: function() {
            if (this.search.length > 0) {
                axios.get('/admin/sender', {
                    "params": {
                        search: this.search
                    }
                }).then((response) => {
                    this.senders = response.data;
                }).catch((error) => {
                    console.log(error);
                });
            }
        },

        selectSender: function (sender) {
            this.tmpSender = sender;
        },

        save: function () {
			this.sender.spidId = this.tmpSender.spidId;
			this.sender.spidDept = this.tmpSender.spidDept;
			this.sender.spidName = this.tmpSender.spidName;
			this.sender.spidTel1 = this.tmpSender.spidTel1;
			this.sender.spidTel2 = this.tmpSender.spidTel2;
			this.sender.spidEmail = this.tmpSender.spidEmail;
			this.sender.job = this.tmpSender.job;
            this.$emit('close');
        }
    },
    
}
</script>

<style scoped>

</style>