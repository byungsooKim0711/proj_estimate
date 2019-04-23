import Vue from 'vue';
const state = {
    prices: []
}

const getters = {
    getPrices (state) {
        return state.prices;
    }
}

const mutations = {
    LOAD_PRICES (state, prices) {
        state.prices = prices;
    },

    DELETE_PRICE (state, price) {
        state.prices = state.prices.filter(p => p.priceId != price.priceId);
    },

    UPDATE_PRICE (state, price) {
        Vue.set(state.prices, state.prices.findIndex(p => p.priceId == price.priceId), price);
    },

    INSERT_PRICE (state, price) {
        state.prices.push(price);
    }
}

const actions = {
    LOAD_PRICES ( {commit}, search ) {
        return axios.get('/admin/price', {
            "params": {
                ...(search ? {search: search}: {})
            }
        }).then((response) => {
            commit('LOAD_PRICES', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    DELETE_PRICE ( {commit}, price ) {
        return axios.delete('/admin/price/' + price.priceId, {

        }).then((response) => {
            if (response.status === 202) {
                commit('DELETE_PRICE', price);
            }
        }).catch((error) => {
            console.log(error);
        });
    },

    UPDATE_PRICE ( {commit}, price) {
        return axios.put('/admin/price/' + price.priceId, price, {

        }).then((response) => {
            commit('UPDATE_PRICE', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    INSERT_PRICE ( {commit}, price) {
        return axios.post('/admin/price', price, {

        }).then((response) => {
            commit('INSERT_PRICE', response.data);
        }).catch((error) => {
            console.log(error);
        });
    }
}

export default {
    state,
    getters,
    mutations,
    actions
}