    
import Vue from 'vue'
import Vuex from 'vuex'
import sender from './modules/sender.js'
import vender from './modules/vender.js'
import tool from './modules/tool.js'
import price from './modules/price.js'
import estimate from './modules/estimate.js'

Vue.use(Vuex);

export const store = new Vuex.Store({
    modules: {
        sender,
        vender,
        tool,
        price,
        estimate
    },
    
    state: {
        currentRoute: '',
    },

    mutations: {},
    
    getters: {},

    actions: {},

    getters: {},
});