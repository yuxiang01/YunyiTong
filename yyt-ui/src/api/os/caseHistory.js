import request from '@/utils/request'

// 查询病历列表
export function listCaseHistory(query) {
  return request({
    url: '/os/caseHistory/list',
    method: 'get',
    params: query
  })
}

// 查询病历详细
export function getCaseHistory(caseId) {
  return request({
    url: '/os/caseHistory/' + caseId,
    method: 'get'
  })
}

// 新增病历
export function addCaseHistory(data) {
  return request({
    url: '/os/caseHistory',
    method: 'post',
    data: data
  })
}

// 修改病历
export function updateCaseHistory(data) {
  return request({
    url: '/os/caseHistory',
    method: 'put',
    data: data
  })
}

// 删除病历
export function delCaseHistory(caseId) {
  return request({
    url: '/os/caseHistory/' + caseId,
    method: 'delete'
  })
}
