from qiskit import QuantumCircuit
from qiskit_aer import AerSimulator
import random
import sys

def get_quantum_suggestion(category):
    qc = QuantumCircuit(2, 2)
    qc.h(0)  # Superposition on qubit 0
    qc.h(1)  # Superposition on qubit 1 (randomness badhane ke liye)
    qc.cx(0, 1)  # Entanglement between qubit 0 and 1
    qc.measure([0, 1], [0, 1])  # Measure both qubits

    simulator = AerSimulator()
    job = simulator.run(qc, shots=1)
    result = job.result()
    counts = result.get_counts()

    # Debug: Counts print karo
  #  print("Counts:", counts)

    outcome = list(counts.keys())[0]
  #  print("Outcome:", outcome)  # Debug: Outcome print karo

    suggestions = {
        "Food": {
            "00": "Cut down on dining out",
            "01": "Buy groceries in bulk",
            "10": "Avoid expensive snacks",
            "11": "Plan meals ahead"
        },
        "Travel": {
            "00": "Use public transport",
            "01": "Carpool with friends",
            "10": "Book tickets early",
            "11": "Avoid peak travel times"
        },
        "Entertainment": {
            "00": "Stream movies at home",
            "01": "Use free event tickets",
            "10": "Limit subscription services",
            "11": "Attend local events"
        },
        "Miscellaneous": {
            "00": "Track small expenses",
            "01": "Set a monthly budget",
            "10": "Avoid impulse buys",
            "11": "Review subscriptions"
        }
    }
    category_suggestions = suggestions.get(category, suggestions["Miscellaneous"])
    return category_suggestions.get(outcome, "No suggestion available")

if __name__ == "__main__":
    category = sys.argv[1] if len(sys.argv) > 1 else "Miscellaneous"
    print(get_quantum_suggestion(category))