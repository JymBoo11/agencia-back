import { useState } from 'react'

interface QuestionCardProps {
  question: string
  onAnswer: (answer: string) => void
  isLoading: boolean
}

export default function QuestionCard({ question, onAnswer, isLoading }: QuestionCardProps) {
  const [answer, setAnswer] = useState('')

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (answer.trim()) {
      onAnswer(answer.trim())
      setAnswer('')
    }
  }

  return (
    <div className="bg-gradient-to-r from-blue-50 to-purple-50 rounded-2xl p-6">
      <p className="text-gray-800 mb-4 font-medium">{question}</p>
      <form onSubmit={handleSubmit} className="space-y-4">
        <textarea
          value={answer}
          onChange={(e) => setAnswer(e.target.value)}
          placeholder="Escribe tu respuesta..."
          className="input-field min-h-[100px] resize-none"
          disabled={isLoading}
        />
        <button
          type="submit"
          disabled={isLoading || !answer.trim()}
          className="btn-primary w-full sm:w-auto"
        >
          Enviar respuesta
        </button>
      </form>
    </div>
  )
}