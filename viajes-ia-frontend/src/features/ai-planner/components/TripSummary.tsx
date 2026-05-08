interface TripSummaryProps {
  conversation: any
  onGenerateRecommendation: () => void
  onCreateTrip: () => void
}

export default function TripSummary({ conversation, onGenerateRecommendation, onCreateTrip }: TripSummaryProps) {
  const { preferences } = conversation

  return (
    <div className="bg-gradient-to-r from-green-50 to-blue-50 rounded-2xl p-6 md:p-8">
      <div className="flex items-center gap-3 mb-6">
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

      {preferences && (
        <div className="bg-white rounded-xl p-6 mb-6 grid grid-cols-1 md:grid-cols-2 gap-4">
          {preferences.destination && (
            <div>
              <p className="text-sm text-gray-500">Destino</p>
              <p className="font-semibold text-gray-900">{preferences.destination}</p>
            </div>
          )}
          {preferences.duration && (
            <div>
              <p className="text-sm text-gray-500">Duración</p>
              <p className="font-semibold text-gray-900">{preferences.duration} días</p>
            </div>
          )}
          {preferences.budget && (
            <div>
              <p className="text-sm text-gray-500">Presupuesto</p>
              <p className="font-semibold text-gray-900">€{preferences.budget}</p>
            </div>
          )}
          {preferences.travelers && (
            <div>
              <p className="text-sm text-gray-500">Viajeros</p>
              <p className="font-semibold text-gray-900">{preferences.travelers} personas</p>
            </div>
          )}
        </div>
      )}
      
      <div className="flex flex-wrap gap-3">
        <button
          onClick={onGenerateRecommendation}
          className="btn-primary flex items-center gap-2"
        >
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
          </svg>
          Generar Recomendación
        </button>
        <button
          onClick={onCreateTrip}
          className="btn-secondary flex items-center gap-2"
        >
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
          </svg>
          Crear Viaje
        </button>
      </div>
    </div>
  )
}