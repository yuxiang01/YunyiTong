import request from '@/utils/request'

// 查询检查项目列表
export function listCheckItem(query) {
  return request({
    url: '/os/checkItem/list',
    method: 'get',
    params: query
  })
}

// 查询检查项目详细
export function getCheckItem(checkId) {
  return request({
    url: '/os/checkItem/' + checkId,
    method: 'get'
  })
}

// 新增检查项目
export function addCheckItem(data) {
  return request({
    url: '/os/checkItem',
    method: 'post',
    data: data
  })
}

// 修改检查项目
export function updateCheckItem(data) {
  return request({
    url: '/os/checkItem',
    method: 'put',
    data: data
  })
}

// 删除检查项目
export function delCheckItem(checkId) {
  return request({
    url: '/os/checkItem/' + checkId,
    method: 'delete'
  })
}
