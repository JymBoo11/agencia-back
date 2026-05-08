import { useState, useEffect } from 'react'
import { useMutation } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'
import api from '../../shared/services/api'

interface Message {
  role: 'user' | 'assistant'
  content: string
}

interface Conversation {
  id: string
  messages: Message[]
  state: string
  preferences: any
}

export default function AiPlannerPage() {
  const [conversation, setConversation] = useState<Conversation | null>(null)
  const navigate = useNavigate()

  const startMutation = useMutation({
    mutationFn: () => api.post('/ai/conversation/start', {}, {
      params: { userId: localStorage.getItem('userId') }
    }).then(res => res.data),
    onSuccess: (data) => setConversation(data)
  })

  const answerMutation = useMutation({
    mutationFn: ({ id, answer }: { id: string; answer: string }) =>
      api.post(`/ai/conversation/${id}/answer`, { answer }).then(res => res.data),
    onSuccess: (data) => setConversation(data)
  })

  useEffect(() => {
    if (!conversation) {
      startMutation.mutate()
    }
  }, [])

  const getLatestAssistantMessage = () => {
    if (!conversation?.messages) return null
    const msgs = conversation.messages.filter(m => m.role === 'assistant')
    return msgs[msgs.length - 1]?.content || ''
  }

  const handleAnswer = (answer: string) => {
    if (!conversation) return
    answerMutation.mutate({ id: conversation.id, answer })
  }

  const getRecommendation = () => {
    if (!conversation) return
    api.post(`/ai/conversation/${conversation.id}/answer`, 
      { answer: 'GENERATE_RECOMMENDATION' })
      .then(res => setConversation(res.data))
  }

  const createTrip = () => {
    if (!conversation) return
    api.post('/trips', {
      destination: conversation.preferences?.destination || 'Destino',
      itinerary: [],
      totalPrice: 1000,
      priceBreakdownJson: '{}'
    }, {
      headers: { 'X-User-Id': localStorage.getItem('userId') }
    }).then(() => navigate('/'))
  }

  if (!conversation) return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50 flex items-center justify-center">
      <div className="text-center">
        <div className="w-16 h-16 border-4 border-blue-600 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
        <p className="text-gray-600 text-lg">Iniciando tu asistente de viajes...</p>
      </div>
    </div>
  )

  const assistantMessage = getLatestAssistantMessage()
  const isReady = conversation.state === 'READY_FOR_RECOMMENDATION' || 
                   conversation.state === 'COMPLETED'

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
      <nav className="bg-white/80 backdrop-blur-md shadow-sm sticky top-0 z-50">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-gradient-to-r from-blue-600 to-purple-600 rounded-xl flex items-center justify-center">
                <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
                </svg>
              </div>
              <h1 className="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                Asistente de Viajes IA
              </h1>
            </div>
            <button 
              onClick={() => navigate('/')}
              className="text-gray-600 hover:text-gray-800 transition-colors"
            >
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
        </div>
      </nav>

      <main className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="card p-6 md:p-8 mb-6">
          <div className="space-y-6 mb-8">
            {conversation.messages.map((msg, i) => (
              <div key={i} className={`flex ${msg.role === 'user' ? 'justify-end' : 'justify-start'}`}>
                <div className={`max-w-lg p-4 rounded-2xl ${
                  msg.role === 'user' 
                    ? 'bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-br-md' 
                    : 'bg-gray-100 text-gray-800 rounded-bl-md'
                }`}>
                  <p className="leading-relaxed">{msg.content}</p>
                </div>
              </div>
            ))}
            
            {answerMutation.isPending && (
              <div className="flex justify-start">
                <div className="bg-gray-100 rounded-2xl rounded-bl-md p-4 flex items-center gap-2">
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce delay-100"></div>
                  <div className="w-2 h-2 bg-gray-400 rounded-full animate-bounce delay-200"></div>
                </div>
              </div>
            )}
          </div>

          {!isReady && (
            <div className="bg-gradient-to-r from-blue-50 to-purple-50 rounded-2xl p-6">
              <p className="text-gray-800 mb-4 font-medium">{assistantMessage}</p>
              <div className="flex gap-3 flex-wrap">
                {['Aventura', 'Playa', 'Cultural', 'Gastronómico', 'Relax'].map((option) => (
                  <button
                    key={option}
                    onClick={() => handleAnswer(option)}
                    disabled={answerMutation.isPending}
                    className="btn-secondary text-sm py-2 px-4"
                  >
                    {option}
                  </button>
                ))}
              </div>
              <div className="mt-4">
                <input
                  type="text"
                  placeholder="O escribe tu respuesta..."
                  className="input-field"
                  onKeyPress={(e) => {
                    if (e.key === 'Enter' && e.currentTarget.value) {
                      handleAnswer(e.currentTarget.value)
                      e.currentTarget.value = ''
                    }
                  }}
                />
              </div>
            </div>
          )}

          {isReady && (
            <div className="bg-gradient-to-r from-green-50 to-blue-50 rounded-2xl p-6">
              <div className="flex items-center gap-3 mb-4">
                <div className="w-12 h-12 bg-green-500 rounded-full flex items-center justify-center">
                  <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                  </svg>
                </div>
                <div>
                  <h3 className="text-xl font-bold text-gray-900">¡Preferencias completadas!</h3>
                  <p className="text-gray-600">Tu viaje está listo para generar</p>
                </div>
              </div>
              
              <div className="flex gap-3 flex-wrap">
                <button
                  onClick={getRecommendation}
                  className="btn-primary"
                >
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                  </svg>
                  Generar Recomendación
                </button>
                <button
                  onClick={createTrip}
                  className="btn-secondary"
                >
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                  </svg>
                  Crear Viaje
                </button>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  )
}