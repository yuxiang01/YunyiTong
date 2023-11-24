import request from '@/utils/request'

// 查询挂号订单列表
export function listRegorder(query) {
  return request({
    url: '/os/regorder/list',
    method: 'get',
    params: query
  })
}

// 查询挂号订单详细
export function getRegorder(regId) {
  return request({
    url: '/os/regorder/' + regId,
    method: 'get'
  })
}

// 新增挂号订单
export function addRegorder(data) {
  return request({
    url: '/os/regorder',
    method: 'post',
    data: data
  })
}

// 修改挂号订单
export function updateRegorder(data) {
  return request({
    url: '/os/regorder',
    method: 'put',
    data: data
  })
}

// 删除挂号订单
export function delRegorder(regId) {
  return request({
    url: '/os/regorder/' + regId,
    method: 'delete'
  })
}
