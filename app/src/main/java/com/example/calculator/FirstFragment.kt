package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.calculator.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.text = "";
        //binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}

        binding.zero.setOnClickListener {
            binding.textView.append(binding.zero.text);
        }

        binding.one.setOnClickListener {
            binding.textView.append(binding.one.text);
        }

        binding.two.setOnClickListener {
            binding.textView.append(binding.two.text);
        }

        binding.three.setOnClickListener {
            binding.textView.append(binding.three.text);
        }

        binding.four.setOnClickListener {
            binding.textView.append(binding.four.text);
        }

        binding.five.setOnClickListener {
            binding.textView.append(binding.five.text);
        }

        binding.six.setOnClickListener {
            binding.textView.append(binding.six.text);
        }

        binding.seven.setOnClickListener {
            binding.textView.append(binding.seven.text);
        }

        binding.eight.setOnClickListener {
            binding.textView.append(binding.eight.text);
        }

        binding.nine.setOnClickListener {
            binding.textView.append(binding.nine.text);
        }

        binding.addition.setOnClickListener {
            binding.textView.append(binding.addition.text);
        }

        binding.subtraction.setOnClickListener {
            binding.textView.append(binding.subtraction.text);
        }

        binding.multiplication.setOnClickListener {
            binding.textView.append(binding.multiplication.text);
        }

        binding.division.setOnClickListener {
            binding.textView.append(binding.division.text);
        }

        binding.sum.setOnClickListener {
            totalSum();
            binding.textView.text = "";
        }

        binding.clear.setOnClickListener {
            ClearCalculate();
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun ClearCalculate()
    {
        binding.textView.text = ""
        binding.sumText.text = ""
    }

    fun totalSum()
    {
        binding.sumText.text = calculateResult()
    }


    private fun calculateResult(): String {
        val seperateDigits = seperateDigits()
        if (seperateDigits.isEmpty()) return ""

        val multiplyDivision = multiplyDivisionCalculate(seperateDigits)
        if (multiplyDivision.isEmpty()) return ""

        val result = addSubtractCalculate(multiplyDivision)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices)
        {
            if (passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nexDigit = passedList[i+1] as Float
                if (operator == '+')
                {
                    result += nexDigit
                }
                if (operator == '-')
                {
                    result -= nexDigit
                }
            }
        }

        return result
    }

    private fun multiplyDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('*') || list.contains('/'))
        {
            list = calcMultiplyDivision(list)
        }
        return list
    }

    private fun calcMultiplyDivision(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices)
        {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when (operator)
                {
                    '*' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex)
            {
                newList.add(passedList[i])
            }
        }

        return newList
    }

    private fun seperateDigits(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.textView.text)
        {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            }
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "")
        {
            list.add(currentDigit.toFloat())
        }

        return list
    }
}