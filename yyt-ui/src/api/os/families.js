import request from '@/utils/request'

// 查询患者家庭关系列表
export function listFamilies(query) {
  return request({
    url: '/os/families/list',
    method: 'get',
    params: query
  })
}

// 查询患者家庭关系详细
export function getFamilies(relatedId) {
  return request({
    url: '/os/families/' + relatedId,
    method: 'get'
  })
}

// 新增患者家庭关系
export function addFamilies(data) {
  return request({
    url: '/os/families',
    method: 'post',
    data: data
  })
}

// 修改患者家庭关系
export function updateFamilies(data) {
  return request({
    url: '/os/families',
    method: 'put',
    data: data
  })
}

// 删除患者家庭关系
export function delFamilies(relatedId) {
  return request({
    url: '/os/families/' + relatedId,
    method: 'delete'
  })
}
