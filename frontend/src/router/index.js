import Vue from 'vue'
import Router from 'vue-router'
import EstimateMain from '@/components/user/router/EstimateMain'
import PageNotFound from '@/components/PageNotFound'
import solution1 from '@/components/user/router/Solution1'
import solution2 from '@/components/user/router/Solution2'

/* admin */
import AdminSender from '@/components/admin/router/AdminSender'
import AdminVender from '@/components/admin/router/AdminVender'
import AdminTool from '@/components/admin/router/AdminTool'
import AdminPrice from '@/components/admin/router/AdminPrice'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'EstimateMain',
      component: EstimateMain
    },
    {
      path: '/solution1',
      name: 'solution1',
      component: solution1
    },
    {
      path: '/solution2',
      name: 'solution2',
      component: solution2
    },
    {
      path: '/tech',
      name: 'EstimateMain',
      component: EstimateMain
    },

    /* admin */
    {
      path: '/admin/sender',
      name: 'AdminSender',
      component: AdminSender
    },
    {
      path: '/admin/vender',
      name: 'AdminVender',
      component: AdminVender
    },
    {
      path: '/admin/tool',
      name: 'AdminTool',
      component: AdminTool
    },
    {
      path: '/admin/price',
      name: 'AdminPrice',
      component: AdminPrice
    },

    // 맨 아래에 *이 있으면 위에 리스트를 제외한 나머지
    {path: '/pagenotfound', component: PageNotFound},
    {path: '*', redirect: '/pagenotfound'}
  ]
})
