import { useParams, useNavigate } from 'react-router-dom'
import { useQuery } from '@tanstack/react-query'
import api from '../../shared/services/api'

export default function TripDetailPage() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()

  const { data: trip, isPending: isLoading } = useQuery({
    queryKey: ['trip', id],
    queryFn: async () => {
      const { data } = await api.get(`/trips/${id}`)
      return data
    }
  })

  const downloadPdf = async () => {
    const response = await api.get(`/trips/${id}/pdf`, { 
      responseType: 'blob' 
    })
    const url = window.URL.createObjectURL(new Blob([response.data]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', `presupuesto_${id}.pdf`)
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }

  if (isLoading) return <div className="p-8 text-center">Cargando...</div>
  if (!trip) return <div className="p-8 text-center">Viaje no encontrado</div>

  return (
    <div className="min-h-screen bg-gray-100">
      <nav className="bg-white shadow-sm p-4">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <h1 className="text-xl font-bold">{trip.title}</h1>
          <button onClick={() => navigate('/')} className="text-blue-600 hover:text-blue-800">
            Volver
          </button>
        </div>
      </nav>

      <main className="max-w-6xl mx-auto p-6">
        <div className="bg-white rounded-lg shadow p-6 mb-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-bold">Presupuesto del Viaje</h2>
            <span className={`px-3 py-1 rounded ${
              trip.status === 'PAID' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'
            }`}>{trip.status}</span>
          </div>

          <div className="text-3xl font-bold text-blue-600 mb-6">€{trip.totalPrice}</div>

          <button onClick={downloadPdf} className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700">
            Descargar PDF
          </button>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-xl font-bold mb-4">Itinerario</h3>
          <pre className="bg-gray-50 p-4 rounded overflow-auto text-sm">
            {trip.itineraryJson || 'No disponible'}
          </pre>
        </div>
      </main>
    </div>
  )
}
