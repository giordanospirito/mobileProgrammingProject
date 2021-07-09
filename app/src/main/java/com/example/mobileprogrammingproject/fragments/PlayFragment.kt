package com.example.mobileprogrammingproject.fragments

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mobileprogrammingproject.R
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileprogrammingproject.data.User
import com.example.mobileprogrammingproject.data.UserViewModel
import com.example.mobileprogrammingproject.databinding.FragmentPlayBinding
import java.lang.Error
import java.lang.Exception
import java.util.*

class PlayFragment : Fragment() {

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    private val diceImages = mutableListOf<Int>()
    private lateinit var animation1: Animation
    private lateinit var animation2: Animation
    private lateinit var animation3: Animation
    private val args:PlayFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //vibration effect
        val vibe = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibeEff = VibrationEffect.createOneShot(50, 100)

        //values that will be passed during play
        var numberRoll : Int = args.rollsNumberArg
        var Score: Int = args.totalScore
        var DoubleBool = args.doubleBool
        var TrisBool = args.trisBool
        var quaterBool = args.quaterBool
        var yahtzeeBool = args.yahtzeeBool
        var FourASCBool = args.fourASCBool
        var FiveASCBool = args.fiveASCBool
        var chanceBool = args.chanceBool
        var BonusBool = args.bonusBool
        var FullBool = args.fullBool
        var lastCombo : String = args.lastCombo1
        var x = args.noRoll


        //where our scoring array will be saved
        val rollResults = mutableListOf<Int>()


        //start binding section
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        //end binding section

        //start see result button
        binding.seeResults.setOnClickListener {
            if(!x){
                //Score = ScoreUpdate(Score,rollResults)
                if(lastCombo=="Coppia"){
                    DoubleBool = true
                }
                if(lastCombo=="Tris"){
                    TrisBool = true
                }
                if(lastCombo=="Quater"){
                    quaterBool = true
                }
                if(lastCombo=="Yahtzee"){
                    yahtzeeBool = true
                }
                if(lastCombo=="Full"){
                    FullBool=true
                }
                if(lastCombo=="Scala da 4"){
                    FourASCBool=true
                }
                if(lastCombo=="Scala da 5"){
                    FiveASCBool=true
                }
                val action = PlayFragmentDirections.actionPlayFragmentToResultsFragment(currentRollsNumberArg = numberRoll,totalScore = Score,doubleBool = DoubleBool, trisBool = TrisBool,quaterBool = quaterBool,yahtzeeBool = yahtzeeBool,fourASCBool = FourASCBool,fiveASCBool = FiveASCBool,fullBool = FullBool,chanceBool = chanceBool,bonusBool = BonusBool,lastCombo2 = lastCombo,noRoll = x)
                findNavController().navigate(action)
            }else{
                Toast.makeText(this.context, "DO A ROLL FIRST", Toast.LENGTH_SHORT).show()
            }
        }
        //end see result button

        //start roll section
        val arrayDices = mutableListOf(binding.FirstRoll, binding.SecondRoll, binding.ThirdRoll, binding.FourthRoll, binding.FifthRoll)
        binding.numberRolls.text = numberRoll.toString()

        diceImagesAdder(diceImages)

        animation1 = AnimationUtils.loadAnimation(this.context, R.anim.shake_animation)
        animation2 = AnimationUtils.loadAnimation(this.context, R.anim.fade_in_delay)
        animation3 = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)

        //setting up listeners
        binding.RollerAndChecker.setOnClickListener {
            binding.RollerAndChecker.isClickable = false
            x = false
            if (numberRoll<13) {
                numberRoll++
                vibe.vibrate(vibeEff)
                rollResults.removeAll(listOf(1, 2, 3, 4, 5, 6))
                binding.dicesRow.visibility = View.VISIBLE
                for (i in 0..4) {
                    getRandomValue(arrayDices[i], rollResults)
                }
                binding.dicesRow.startAnimation(animation3)
                //binding.numberRolls.startAnimation(animation2)
                binding.ComboReader.startAnimation(animation2)
                binding.Score.startAnimation(animation2)
                binding.numberRolls.text = numberRoll.toString()
                binding.ComboReader.text = getCombo(rollResults)
                binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
                lastCombo = getCombo(rollResults)

                //user should want to change some dice results
                binding.FirstRoll.isClickable = true
                binding.SecondRoll.isClickable = true
                binding.ThirdRoll.isClickable = true
                binding.FourthRoll.isClickable = true
                binding.FifthRoll.isClickable = true


                if (numberRoll == 13) {
                    binding.RollerAndChecker.text = "End game"
                }
            } else {
                val action = PlayFragmentDirections.actionPlayFragmentToEndGameFragment()
                findNavController().navigate(action)
            }


        }
        binding.FirstRoll.setOnClickListener {
            val random = Random().nextInt(6)
            arrayDices[0].startAnimation(animation1)
            arrayDices[0].setImageResource(diceImages.elementAt(random))
            rollResults.removeAt(0)
            rollResults.add(0,random+1)
            binding.ComboReader.text = getCombo(rollResults)
            binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
            binding.FirstRoll.isClickable = false
            lastCombo = getCombo(rollResults)
        }
        binding.SecondRoll.setOnClickListener {
            val random = Random().nextInt(6)
            arrayDices[1].startAnimation(animation1)
            arrayDices[1].setImageResource(diceImages.elementAt(random))
            rollResults.removeAt(1)
            rollResults.add(1,random+1)
            binding.ComboReader.text = getCombo(rollResults)
            binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
            binding.SecondRoll.isClickable = false
            lastCombo = getCombo(rollResults)
        }
        binding.ThirdRoll.setOnClickListener {
            val random = Random().nextInt(6)
            arrayDices[2].startAnimation(animation1)
            arrayDices[2].setImageResource(diceImages.elementAt(random))
            rollResults.removeAt(2)
            rollResults.add(2,random+1)
            binding.ComboReader.text = getCombo(rollResults)
            binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
            binding.ThirdRoll.isClickable = false
            lastCombo = getCombo(rollResults)
        }
        binding.FourthRoll.setOnClickListener {
            val random = Random().nextInt(6)
            arrayDices[3].startAnimation(animation1)
            arrayDices[3].setImageResource(diceImages.elementAt(random))
            rollResults.removeAt(3)
            rollResults.add(3,random+1)
            binding.ComboReader.text = getCombo(rollResults)
            binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
            binding.FourthRoll.isClickable = false
            lastCombo = getCombo(rollResults)
        }
        binding.FifthRoll.setOnClickListener {
            val random = Random().nextInt(6)
            arrayDices[4].startAnimation(animation1)
            arrayDices[4].setImageResource(diceImages.elementAt(random))
            rollResults.removeAt(4)
            rollResults.add(4,random+1)
            binding.ComboReader.text = getCombo(rollResults)
            binding.Score.text = "${getScore(getCombo(rollResults)).toString()} points!"
            binding.FifthRoll.isClickable = false
            lastCombo = getCombo(rollResults)
        }



        //end roll section

        //return section
        return view
    }


    private fun ScoreUpdate(score: Int, rollResults: MutableList<Int>): Int {
        return score + getScore(getCombo(rollResults))
    }


    //functions
    private fun getRandomValue(dice: ImageView?,list: MutableList<Int>){
        val random = Random().nextInt(6)
        dice?.startAnimation(animation1)
        dice?.setImageResource(diceImages.elementAt(random))
        list.add(random+1)
    } //get and add random value to rollResult. set right diceview
    private fun diceImagesAdder(diceImagesList: MutableList<Int>){
        diceImagesList.add(R.drawable.ic_dice_one)
        diceImagesList.add(R.drawable.ic_dice_two)
        diceImagesList.add(R.drawable.ic_dice_three)
        diceImagesList.add(R.drawable.ic_dice_four)
        diceImagesList.add(R.drawable.ic_dice_five)
        diceImagesList.add(R.drawable.ic_dice_six)
    } //given dicelist, adds to this the dices images of 1 to 6 dices
    private fun getScore(s: String): Int {
        val value = when(s){
            "Coppia" -> 5
            "Tris"->10
            "Quater" -> 20
            "Yahtzee" ->50
            "Scala da 4" ->30
            "Scala da 5" -> 40
            "Full" ->25
            else -> 0
        }
        return value
    }

    // Combo algorithm fun section
    private fun getCombo(List: MutableList<Int>): String {
        if (FiveASCorder(List)) {
            return "Scala da 5"
        } else {
            if (FourASCorder(List)) {
                return "Scala da 4"
            } else {
                for (i in 1..6) {
                    for (j in 1..6) {
                        if (isYahtzee(List,i)) {
                            return "Yahtzee"
                        } else {
                            if (isQuater(List,i)) {
                                return "Quater"
                            } else {
                                if (isFull(List)) {
                                    return "Full"
                                } else {
                                    if (isTriple(List, i)) {
                                        return "Tris"
                                    } else {
                                        if (isDouble(List, i)) {
                                            return "Coppia"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "NO combo"
    }

    private fun isFull(list: MutableList<Int>): Boolean {
        for (i in 1..6){
            if(isTriple(list,i)){
                for(j in 1..6){
                    if((i != j) and isDouble(list,j)){
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun BonusUsed(list: MutableList<Int>): Boolean {
        return false
    }

    private fun FiveASCorder(list: MutableList<Int>): Boolean {
        for(i in 0..4){
            if(list[i]<=list[i+1]){
                return false
            }
        }
        return true
    }

    private fun FourASCorder(list: MutableList<Int>): Boolean {
        for(i in 0..3){
            if(list[i]<=list[i+1]) {
                for (j in 1..4) {
                    if (list[j] <= list[j + 1]) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun isYahtzee(List: MutableList<Int>, number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter > 6) {
                    return true
                }
            }
        }
        return false
    }

    private fun isQuater(List: MutableList<Int>, number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 6) {
                    return true
                }
            }
        }
        return false
    }

    private fun isTriple(List: MutableList<Int>, number:Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 3) {
                    return true
                }
            }
        }
        return false
    }

    private fun isDouble(List: MutableList<Int>,number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 1) {
                    return true
                }
            }
        }
        return false
    }

}

