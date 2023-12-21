import request from '@/utils/request'

// 查询处方订单列表
export function listRecipe(query) {
  return request({
    url: '/os/recipe/list',
    method: 'get',
    params: query
  })
}

// 查询处方订单详细
export function getRecipe(preCode) {
  return request({
    url: '/os/recipe/' + preCode,
    method: 'get'
  })
}

// 新增处方订单
export function addRecipe(data) {
  return request({
    url: '/os/recipe',
    method: 'post',
    data: data
  })
}

// 修改处方订单
export function updateRecipe(data) {
  return request({
    url: '/os/recipe',
    method: 'put',
    data: data
  })
}

// 删除处方订单
export function delRecipe(preCode) {
  return request({
    url: '/os/recipe/' + preCode,
    method: 'delete'
  })
}
