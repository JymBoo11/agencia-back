import api from '../../shared/services/api'

export const tripApi = {
  getTrips: () => api.get('/trips'),
  getTrip: (id: string) => api.get(`/trips/${id}`),
  createTrip: (data: any) => api.post('/trips', data),
  updateStatus: (id: string, status: string) => api.patch(`/trips/${id}/status?status=${status}`),
  downloadPdf: (id: string) => api.get(`/trips/${id}/pdf`, { responseType: 'blob' })
}