import api from '../../shared/services/api'

export const aiApi = {
  startConversation: (userId: number) =>
    api.post('/ai/conversation/start', {}, { params: { userId } }),

  sendAnswer: (conversationId: string, answer: string) =>
    api.post(`/ai/conversation/${conversationId}/answer`, { answer }),

  getConversation: (conversationId: string) =>
    api.get(`/ai/conversation/${conversationId}`)
}