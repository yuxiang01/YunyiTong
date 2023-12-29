import request from '@/utils/request'

// 查询体格列表
export function listPhysique(query) {
  return request({
    url: '/os/physique/list',
    method: 'get',
    params: query
  })
}

// 查询体格详细
export function getPhysique(physiqueId) {
  return request({
    url: '/os/physique/' + physiqueId,
    method: 'get'
  })
}

// 新增体格
export function addPhysique(data) {
  return request({
    url: '/os/physique',
    method: 'post',
    data: data
  })
}

// 修改体格
export function updatePhysique(data) {
  return request({
    url: '/os/physique',
    method: 'put',
    data: data
  })
}

// 删除体格
export function delPhysique(physiqueId) {
  return request({
    url: '/os/physique/' + physiqueId,
    method: 'delete'
  })
}
