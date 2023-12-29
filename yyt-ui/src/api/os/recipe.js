import request from '@/utils/request'

// 查询处方订单列表
export function listOrder(query) {
  return request({
    url: '/os/order/list',
    method: 'get',
    params: query
  })
}

// 查询处方订单详细
export function getOrder(preCode) {
  return request({
    url: '/os/order/' + preCode,
    method: 'get'
  })
}

// 查询处方订单详细 by regId
export function getOrderByRegId(regId) {
  return request({
    url: '/os/order/reg/' + regId,
    method: 'get'
  })
}

// 新增处方订单
export function addOrder(data) {
  return request({
    url: '/os/order',
    method: 'post',
    data: data
  })
}

export function saveOrder(data) {
  return request({
    url: '/os/order/case',
    method: 'post',
    data: data
  })
}

export function recipeFinish(data) {
  return request({
    url: '/os/order/finish',
    method: 'post',
    data: data
  })
}

export function recipePay(data) {
  return request({
    url: '/os/order/pay',
    method: 'post',
    data: data
  })
}

// 修改处方订单
export function updateOrder(data) {
  return request({
    url: '/os/order',
    method: 'put',
    data: data
  })
}

// 删除处方订单
export function delOrder(preCode) {
  return request({
    url: '/os/order/' + preCode,
    method: 'delete'
  })
}
