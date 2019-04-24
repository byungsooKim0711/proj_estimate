import Vue from 'vue';
const state = {
    estimates: []
}

const getters = {
    getEstimates (state) {
        return state.estimates;
    }
}

const mutations = {
    LOAD_ESTIMATES (state, estimates) {
        state.estimates = estimates;
    },

    DELETE_ESTIMATE (state, estimate) {
        
    },

    UPDATE_ESTIMATE (state, estimate) {
        
    },

    INSERT_ESTIMATE (state, estimate) {
        
    }
}

const actions = {
    LOAD_ESTIMATES ( {commit} ) {
        return axios.get('/admin/estimate', {

        }).then((response) => {
            commit('LOAD_ESTIMATES', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    DELETE_ESTIMATE ( {commit}, estimate ) {

    },

    UPDATE_ESTIMATE ( {commit}, estimate) {

    },

    INSERT_ESTIMATE ( {commit}, estimate) {

    }
}

export default {
    state,
    getters,
    mutations,
    actions
}